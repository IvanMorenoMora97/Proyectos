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

            .td{

                text-align: center;

            }

             #tdverde {
                background-color: green;
            }

            #tdrojo {
                background-color: red;
            }

            #tdblanco {
                background-color: white;
            }

        </style>
    </head>
    <body>
        <div class="flex-center position-ref full-height">
            

        	

        	


                                   <table>

                                    <caption>Correccion de problemas subidos por los usuarios</caption>

                                    <tr>
                                    
                                        <th>Usuari</th>
                                        @foreach($problemas as $prob)
                                        <th><a href="{{ route('send',$prob->IdProblema)}}">{{$prob->Titol}}</a></th>
                                        @endforeach 
                                        <th>Punts</th>
                                        <th>Ranking</th>
                                    
                                    </tr>
								@php
                                    $pos=0;
                                @endphp

                                    @foreach($usuarios as $usu)

                                    @if($usu->professor==0)

                                    

                                    <tr>
                                        <td class="td">{{$usu->name}}</td>
                                            
                                        @foreach($problemas as $prob)
										@php
                                            $i=0;
                                            $correcto = false;
										@endphp
                                            @foreach($ranking_user as $submit)

                                               @if($submit->resolt==1 or $submit->resolt==2)                                                
                                                        @if($submit->IdUsuario==$usu->id and $submit->IdProblema==$prob->IdProblema and $submit->resolt==1 and $correcto==false)
                                                            
                                                        	@php
                                                            $i=1;
                                                            @endphp
                                                            @elseif($submit->IdUsuario==$usu->id and $submit->IdProblema==$prob->IdProblema and $submit->resolt==2)
                                                           @php
                                                            $i=2;
                                                            $correcto=true;
                                                            @endphp
                                                        @endif
                                                    @endif

                                            @endforeach 

                                            @if($i==2)
                                                <td><button id="tdverde" src=""><a href="{{ route('codigoresuelto',['problemId'=>$prob->IdProblema,'userId'=>$usu->id])}}">Corregir</a></button></td>
                                            @elseif($i==1)
                                                <td><button  id="tdrojo"><a href="{{ route('codigoresuelto',['problemId'=>$prob->IdProblema,'userId'=>$usu->id])}}">Corregir</a></button></td>
                                                @else
                                                <td id="tdblanco"></td>
                                            @endif
                                            @endforeach

                                            <td>{{$usu->puntuacion}}</td>
                                            <td>{{++$pos}}</td> 
                                    </tr>
                                    @endif
                                    @endforeach 
                                </table>



        </div>
    
    </body>
</html>
