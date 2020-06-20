<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Laravel</title>

        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,600" rel="stylesheet">

        <!-- Styles -->
        <style>
            html, body {
                background-color: #fff;
                color: #636b6f;
                font-family: 'Nunito', sans-serif;
                font-weight: 200;
                height: 100vh;
                margin: 0;
            }

            .full-height {
                height: 100vh;
            }

            /*.flex-center {
                align-items: center;
                display: flex;
                justify-content: center;
            }*/

            .position-ref {
                position: relative;
            }

            .top-right {
                position: absolute;
                right: 10px;
                top: 18px;
            }

            .content {
                text-align: center;
            }

            .title {
                font-size: 84px;
            }

            .links > a {
                color: #636b6f;
                padding: 0 25px;
                font-size: 13px;
                font-weight: 600;
                letter-spacing: .1rem;
                text-decoration: none;
                text-transform: uppercase;
            }

            .m-b-md {
                margin-bottom: 30px;
            }

            

        </style>
    </head>
    <body>

        

        <!-- VER PDF: https://www.youtube.com/watch?v=MfrguQI1jQ0 -->

        <div class="flex-center position-ref full-height">

            <?php
             $path2 = null;
             $titulo = null;

            ?>
          
               
                <p>ENUNCIAT: {{$problemas->Enunciat}}</p>               
                
          
        @if($user->professor==0)
        <form method="POST" action="/compilar" enctype="multipart/form-data">
         <p>Selecciona el fichero java</p>

         <!--     Campo oculto para enviar el titulo del problema, in y out-->
         
            <input type="hidden" name="id" value="{{$problemas->IdProblema}}">
            <input type="hidden" name="titol" value="{{$problemas->Titol}}">
          <input type="file" name="problema" class="form-control">
          <input type="hidden" name="puntuacio" value="{{$problemas->Puntuacio}}">
        <button type="submit" id="botonsubir">SUBIR PROBLEMA</button>

        {{ csrf_field() }} 
        </form>
        @endif



            <iframe src="{{url('uploads/'.$problemas->Path)}}" width="100%" height="1000"></iframe>

            </div>
        
    </body>
</html>
