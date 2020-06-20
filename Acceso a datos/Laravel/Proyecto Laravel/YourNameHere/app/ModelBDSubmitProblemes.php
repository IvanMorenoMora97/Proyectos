<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ModelBDSubmitProblemes extends Model
{
    protected $table = 'SubmitProblemes';
    protected $fillable = ['IdUsuario', 'IdProblema', 'resolt', 'codi_resolt'];
}

