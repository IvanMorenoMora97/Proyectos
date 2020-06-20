<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateSubmitProblemesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('SubmitProblemes', function (Blueprint $table) {
			$table->bigIncrements('IdSubmitSolucion');
            $table->integer('IdUsuario');
            $table->integer('IdProblema');
            $table->boolean('resolt')->nullable();
            $table->text('codi_resolt');
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
        Schema::dropIfExists('SubmitProblemes');
    }
}






