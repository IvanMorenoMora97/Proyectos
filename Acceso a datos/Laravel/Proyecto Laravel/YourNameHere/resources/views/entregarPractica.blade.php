@extends('layouts.app')

@section('content')
<div class="container">
                <h2>Add New Problema</h2>
               

@if($user->professor)
<form method="POST" action="/subirproblema" enctype="multipart/form-data">

   <div class="form-group">
        <p>Titulo del problema</p>
        <textarea name="titol" class="form-control"></textarea>
    </div>

    <div class="form-group">
        <p>Enunciado</p>
        <input name="enunciat" class="form-control">
    </div>
	
	   <div class="form-group">
        <p>Puntuación del problema</p>
        <input name="puntuacio" class="form-control">
    </div>
	
	   <div class="form-group">
        <p>Fichero PDF</p>
        <input type="file" name="problema" class="form-control">
        <p>Fichero entrada</p>
        <input type="file" name="in" class="form-control">
        <p>Fichero Salida</p>
        <input type="file" name="out" class="form-control">
    </div>


    <div class="form-group">
        <button type="submit" class="btn btn-primary">Pujar Problema</button>
    </div>
    
    
    
{{ csrf_field() }}
</form>

@else
        <div>
        <p>Acceso denegado, solo pueden entrar los profesores.</p>
        </div>
    @endif

</div>
@endsection
