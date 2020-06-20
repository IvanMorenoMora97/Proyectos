<?php

namespace App\Http\Controllers;
use Auth;
use Illuminate\Http\Request;
use App\User;
use App\ModelBDSubmitProblemes;
use App\ModelBDProblemes;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index()
    {
        $userId = Auth::id();
        $user = Auth::user();
         
        $resolt2 = ModelBDSubmitProblemes::where('IdUsuario','=',$userId)
        ->where('resolt','=','2')
        ->select('IdProblema')
        ->groupBy('IdProblema')
        ->get();

        $allsubmissions = ModelBDSubmitProblemes::where('IdUsuario','=',$userId)
        ->get(); 

        $problemas = ModelBDProblemes::all();

        return view('home',compact('user'),compact('resolt2','problemas','allsubmissions'));
        //return view('home');
    }
}
