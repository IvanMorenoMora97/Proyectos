<?php

namespace App\Http\Controllers;
use Auth;
//use App\Auth;
use App\Practica;
//importa tota la DB
use DB;
//importa el model User, equivalent a la taula users
use App\User;
use Illuminate\Http\Request;

class PracticasController extends Controller
{

//ejemplos de funciones
    public function FuncionesRandom(){
        //array de PHP con todas las practicas
        $practicas = Practica::all() ;
        //count practicas
        Practica::all()->count();
        //where. si. not a prank
        //Practica::Where('id','=','4');
        //devuelve true si estas registrado
        $bool = Auth::check();
        //devuelve el usuario registrado
        $userId = Auth::id();
        $user = Auth::user();
        $practicas_user = Practica::Where('user_id','=',$userId);
    }
    public function hola()
    {
        return view('hola');
    }

    public function verMisPracticas()
    {
        //  ¯\_(ツ)_/¯

        /*$user = Auth::user();
		
        return view('misPracticas',compact('user'));*/
		$userId = Auth::id();
        $user = Auth::user();
		//$practicas_user =Practica::all();
        $practicas_user = ModelBDProblemes::where('user_id','=',$userId)->get();
		return view('misPracticas',compact('practicas_user'),compact('user'));
		//return $practicas_user->toJson();
    }

    public function nuevaPractica()
    {
        $practica = new Practica();
        $practica->contenido = 'a';
        $practica->user_id = Auth::id();
        $practica->save();

        return redirect('misPracticas');

    }

    public function entregarPractica()
    {
        return view('entregarPractica');
    }
	
	public function java()
    {	
		//$out = shell_exec('javac C:\\xampp\\htdocs\\YourNameHere\\YourNameHere\\public\\uploads\\Prova.java');
		//$out .= shell_exec('java \\uploads\\Prova');
		//echo '<pre>'.$out.'</pre>';	
		//$out = shell_exec('javac -version 2>&1');
		chdir('D:\\Laravel\\YourNameHere.v3\\YourNameHere\\public\\uploads');
		//$out = shell_exec('javac C:\\xampp\\htdocs\\YourNameHere\\YourNameHere\\public\\uploads\\Prova.java 2>&1');
		//$out .= shell_exec('java C:\\xampp\\htdocs\\YourNameHere\\YourNameHere\\public\\uploads\\Prova 2>&1');
		//exec('java C:\\xampp\\htdocs\\YourNameHere\\YourNameHere\\public\\uploads\\Prova.java 2>&1', $out1);
		//$out1 = shell_exec('java -version 2>&1');
		$out = shell_exec('javac Prova.java');
		$out1 = shell_exec('java Prova 2>&1');
		
        return var_dump($out1);
    }

    ///viene de un post de un formulario con lo que tiene un request
    public function subirPractica(Request $Request)
    {
        //validacion, si no la cumple peta
        $Request->validate([
            //el campo file es obligatorio, debe ser extension pdf xlx o csv, tamaño maximo 2MB
            'file' => 'required|mimes:pdf,xlx,csv|max:2048',
        ]);

        //nombre del fichero sacado a aprtir del time
        $fileName = time().'.'.$Request->file->extension();

        //mover el fichero a la capeta public/uploads con el nombre generado anteriorkmente
        $Request->file->move(public_path('uploads'), $fileName);

        $practica = new Practica();
        $practica->contenido = $Request->contenido;
        $practica->user_id = Auth::id();
        //la nueva variable path
        $practica->path = $fileName;
        $practica->save();

        //puedes añadirle un campo a un redirect o return
        return redirect('misPracticas')->with('success','Practica entregada.');

    }
}
