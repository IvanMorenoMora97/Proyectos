<?php

namespace App\Http\Controllers;
use Auth;
//use App\Auth;
use App\ModelBDProblemes;
use App\ModelBDSubmitProblemes;
//importa tota la DB
use DB;
//importa el model User, equivalent a la taula users
use App\User;
use Illuminate\Http\Request;

class ProblemesControllers extends Controller
{

//ejemplos de funciones
    public function FuncionesRandom(){
        //array de PHP con todas las practicas
        $practicas = ModelBDProblemes::all() ;
        //count practicas
        ModelBDProblemes::all()->count();
        //where. si. not a prank
        //Practica::Where('id','=','4');
        //devuelve true si estas registrado
        $bool = Auth::check();
        //devuelve el usuario registrado
        $userId = Auth::id();
        $user = Auth::user();
        $practicas_user = ModelBDProblemes::Where('user_id','=',$userId);
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
        $practicas_user = ModelBDSubmitProblemes::where('IdUsuario','=',$userId)->get();
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
    public function subirProblema(Request $Request)
    {
        //validacion, si no la cumple peta
         $Request->validate([
            //el campo file es obligatorio, debe ser extension pdf xlx o csv, tamaño maximo 2MB
            'problema' => 'required|mimes:pdf,xlx,csv|max:2048',
        ]);

        //nombre del fichero sacado a aprtir del time
        $fileName = time().'.'.$Request->problema->extension();
        $fileName=$Request->titol;
        //mover el fichero a la capeta public/uploads con el nombre generado anteriorkmente
        $Request->problema->move(public_path('uploads/problemas/'.$fileName), $fileName.".pdf");
        $Request->in->move(public_path('uploads/problemas/'.$fileName), "in.txt");
        $Request->out->move(public_path('uploads/problemas/'.$fileName), "out.txt");
        $problema = new ModelBDProblemes();
        $problema->Titol = $Request->titol;
        $problema->Enunciat = $Request->enunciat;
        $problema->Puntuacio = $Request->puntuacio;
        //$problema->user_id = Auth::id();
        //la nueva variable path
        $problema->path = "/problemas/" .$fileName."/".$fileName.".pdf";
        $problema->in="/problemas/" .$fileName."/in.txt";
        $problema->out="/problemas/" .$fileName."/out.txt";
        $problema->save();

        //puedes añadirle un campo a un redirect o return
        return redirect('rank')->with('success','Problema entregada.');

    }
	
	
	public function problemas()
    {
        $userId = Auth::id();
        $user = Auth::user();
         
         
        $practicas_user = ModelBDSubmitProblemes::where('IdUsuario','=',$userId)->get();
		return view('entregarPractica',compact('practicas_user'),compact('user'));
    }

    public function rankings()
    {
        $userId = Auth::id();
        $user = Auth::user();
         
        $problemas = ModelBDProblemes::all();
        $usuarios = User::orderBy('puntuacion', 'DESC')->get();    
        $ranking_user = ModelBDSubmitProblemes::all();
        
       
        return view('rankings',compact('problemas','usuarios','ranking_user'));
        
        
        
    }

    public function create()
    {
        return view('create');
    }

    public function codigoresuelto(Request $request, $idproblem, $iduser) {

        $problemas = ModelBDProblemes::where('IdProblema', $idproblem)->first();
        $submissions = ModelBDSubmitProblemes::where('IdProblema', $idproblem)
        ->where('IdUsuario', $iduser)
        ->orderBy('IdSubmitSolucion','DESC')->take(1)->get();   

        return view('codigoresuelto',compact('problemas','submissions'));
    }

    public function send(Request $request, $id)
    {
        $userId = Auth::id();
        $user = Auth::user();
        
        $problemas = ModelBDProblemes::where('IdProblema', $id)->first();

        return view('send',compact('problemas','user'));
    }

     public function check()
    {
        $userId = Auth::id();
        $user = Auth::user();
         
        $problemas = ModelBDProblemes::all();
        $usuarios = User::orderBy('puntuacion', 'DESC')->get();    
        $ranking_user = ModelBDSubmitProblemes::all();
        
       
        return view('check',compact('problemas','usuarios','ranking_user'));
      //  return view('check');
    }

        //Metodo por definir    
    public function compilar(Request $Request)
    {
    // Shell__exec lo que permite es guardarlo en una variable para posteriormente visualizarlo
    //Cogemos el nombre de el archivo java, y lo enviamos a la carpeta submits en /upload/submit
    $file = $Request->problema->getClientOriginalName();
    $fileName = pathinfo($file,PATHINFO_FILENAME);
    $Request->problema->move(public_path('uploads/submits/'), $fileName.".java");
        

    // Creamos un objeto submitproblemes
    $problemasubmit = new ModelBDSubmitProblemes();
    $problemasubmit->IdUsuario = Auth::id();
    $problemasubmit->IdProblema = $Request->id;
    $problemasubmit->codi_resolt=$texto=file_get_contents('uploads/submits/'.$fileName.".java");
    $env = NULL;
    $options = ["bypass_shell" => true];
    $cwd = NULL;
    $descriptorspec = [
        0 => ["pipe", "r"],     //stdin is a pipe that the child will read from
        1 => ["pipe", "w"],     //stdout is a pipe that the child will write to
        2 => ["file", "java.error", "a"]
    ];

    // Fichero a seleccionar 
    $titol=$Request->titol;
    $input=file_get_contents('uploads/problemas/'.$titol.'/in.txt');
    //Compilar fichero
    shell_exec('javac uploads/submits/'.$fileName.".java");
    //Proc open nos permite ejecutar un script dentro del cmd mediante pipes
    chdir('uploads/submits/');
    $process = proc_open('java '.$fileName, $descriptorspec, $pipes, $cwd, $env, $options);
    if (is_resource($process)) {
         //feeding text to java
        fwrite($pipes[0], $input);
        fclose($pipes[0]);

        //reading output text from java
        $output = stream_get_contents($pipes[1]);
        //Para quitar el retorno de carro del output
        $output = substr($output, 0, -2);
        fclose($pipes[1]);

        $return_value = proc_close($process);
    }

    chdir('../');
    $input=file_get_contents('problemas/'.$titol.'/out.txt');
    echo " El input del programa es ".$input . "<br>";

    echo " El output del programa da ".$output. "<br>";


    if ($input===$output){
        
        $usuario = Auth::user();

        $usuario->puntuacion = $usuario->puntuacion + $Request->puntuacio;
        $usuario->save();

        //echo "Es igual <br>";
        $problemasubmit->Resolt=2;
    }
    else {
        
        //echo "No es igual <br>";
        
        $problemasubmit->Resolt=1;
    }


      $problemasubmit->save();
    return redirect('/')->with('success','problema corregit');   
        }

    }






	

