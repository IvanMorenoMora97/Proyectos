<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ModelBDProblemes extends Model
{
    protected $table = 'Problemas';
   	protected $fillable = ['Titol', 'Enunciat', 'Puntuacio', 'FitxerPDF','in','out'];
}
