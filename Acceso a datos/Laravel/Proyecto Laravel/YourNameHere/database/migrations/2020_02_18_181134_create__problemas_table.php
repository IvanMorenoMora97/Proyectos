<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateProblemasTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('Problemas', function (Blueprint $table) {
            $table->bigIncrements('IdProblema');
            $table->string('Titol', 255);
            $table->text('Enunciat');
            $table->text('Puntuacio');
            $table->string('Path')->nullable();
            $table->boolean('FitxerPDF')->nullable();
            $table->string('in')->nullable();
            $table->string('out')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('Problemas');
    }
}
