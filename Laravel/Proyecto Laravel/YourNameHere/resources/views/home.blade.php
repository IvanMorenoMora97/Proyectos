@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">Dashboard</div>

                <div class="card-body">
                    @if (session('status'))
                        <div class="alert alert-success" role="alert">
                            {{ session('status') }}
                        </div>
                    @endif

                    You are logged in!
                </div>
                
                                
                <!-- PROFESORES -->
            @if($user->professor)
                <a href="/problemas">Crear un problema</a><br>
                <a href="/check">Corregir problema</a><br>
                <a href="/rank">Ranking </a><br>
                
                 <!-- ALUMNOS -->
            @else
               <!-- <a href="/send">Problemas para resolver</a><br>-->
                
                <a href="/rank">Ranking de alumnos y problemas para hacer</a><br>
                <p>Puntuación: {{$user->puntuacion}}</p>
      
                <p>Has resuelto los siguientes ejercicios:</p>
                

                @foreach($resolt2 as $prac)
                   @foreach($problemas as $p)
                        @if($prac->IdProblema==$p->IdProblema)
                            <p>Problema: {{$p->Titol}} OK</p>
                                                        
                        @endif
                   @endforeach
                @endforeach

                <p>Submissions:</p>
                @foreach($allsubmissions as $sub)
                    @if($sub->resolt==2)
                    <p>{{$sub->created_at}} Problema: {{$sub->IdProblema}} <span style="color: green">OK</span></p>
                    @else
                        <p>{{$sub->created_at}} Problema: {{$sub->IdProblema}} <span style="color: red">WRONG ANSWER</span></p>
                    @endif
                @endforeach

            @endif
            </div>
		</div>
    </div>
</div>
@endsection
