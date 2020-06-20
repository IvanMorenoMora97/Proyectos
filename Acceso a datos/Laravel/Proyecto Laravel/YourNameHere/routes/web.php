<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

/*Route::get('/', function () {
    return view('welcome');
});*/

Auth::routes();

Auth::routes();

Auth::routes();

//Route::get('/home', 'HomeController@index')->name('home');


/*RUTAS*/

/*“/” tots els problemes, marcant en quins has enviat resposta, i si la resposta es correcta o no. Similar a la vista de team en el DOMjudge.
“/” per als professors serà diferent. Mostrarà totes les submissions que queden per corregir
“/rank” tots els alumnes, amb els seus punts, ordenats per punts. Igual per a professors i alumnes.
“/create” i “/send” Seran tipus formulari.
“/check/{id]” serà sobre la ID de la submission. Mostrarà el codi (o fitxer java) i podràs acceptar-lo o no mitjançant dos botons.
*/


Route::get('/', 'HomeController@index')->name('home');	/*SERA EL DIRECTORIO PRINCIPAL, EL CUAL SERA LA PRIMERA VISTA QUE VERAN LOS ALUMNOS Y PROFESORES*/

Route::get('/verproblemas', 'ProblemesControllers@verMisPracticas')->name('taulesproblemes');

Route::post('/subirproblema', 'ProblemesControllers@subirProblema');

Route::get('/problemas','ProblemesControllers@problemas');


Route::get('/rank','ProblemesControllers@rankings');	/*MOSTRARA LA VISTA DE LOS RANKINGS DE TODOS LOS ALUMNOS*/

Route::get('/create','ProblemesControllers@create');	/*PAGINA DE TIPO FORMULARIO*/

Route::get('/send/{id}','ProblemesControllers@send')->name('send');		/*PAGINA DE TIPO FORMULARIO*/
														
Route::get('/check','ProblemesControllers@check');		/*MOSTRARÀ EL CODIGO O FICHERO JAVA Y MEDIANTE UN BOTON PODRAS ACEPTARLO O NO SUPONGO QUE SERA PARA QUE EL 
															PROFE ACEPTE EL CODIGO O ALGO ASI*/	

Route::get('/verProblema','ProblemesControllers@verProblema');

Route::post('/compilar', 'ProblemesControllers@compilar');

Route::get('/codigoresuelto/{problemId}/{userId}', 'ProblemesControllers@codigoresuelto')->name('codigoresuelto');


