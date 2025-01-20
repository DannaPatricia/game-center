<?php
    function generaTexto($nameHtml, $nombreCampo, $valor, $esValorCorrecto, $placeHolder){
        $mensaje = !$esValorCorrecto ? '<span style="color:red">Campo obligatorio</span>' : '';
        return '<div class="input-container">
            <label for="'.$nameHtml.'">'.ucfirst($nombreCampo).'</label>
            <input type="text" name="'.$nameHtml.'" id="'.$nameHtml.'" value = "'.$valor.'" placeholder="'.$placeHolder.'"/>'.
            $mensaje.'
        </div>';
    }

    function generaPassword($esValorCorrecto){
        $mensaje = !$esValorCorrecto ? '<span style="color:red">Contraseña de usuario obligatoria</span>' : '';
        return '<div class="input-container">
            <label for="clave">Clave</label>
            <input type="password" name="clave" id="nombre" placeholder="Inserte clave de usuario"/>'.
            $mensaje.'
        </div>';
    }

    function generaCampoNumerico($nameHtml, $nombreCampo, $valor, $esValorCorrecto, $placeHolder, $mensaje){
        $mensaje = !$esValorCorrecto ? '<span style="color:red">Campo obligatorio, '.$mensaje.'</span>' : '';
        return '<div class="input-container">
            <label for="'.$nameHtml.'">'.ucfirst($nombreCampo).'</label>
            <input type="number" name="'.$nameHtml.'" id="'.$nameHtml.'" value = "'.$valor.'" placeholder="'.$placeHolder.'"/>'.
            $mensaje.'
        </div>';
    }

    function generaRadio($nameHtml, $nombreCampo, $arrayValores, $esValorCorrecto, $valor){
        $mensaje = !$esValorCorrecto ? '<span style="color:red">Campo obligatorio</span>' : '';
        $html = '<div class="input-container">
            <label for="'.$nameHtml.'">'.ucfirst($nombreCampo).'</label>';
        foreach ($arrayValores as $valorAux) {
        $html .= '<input type="radio" name="'.$nameHtml.'" value="' . $valorAux . '" ' . ($valor === $valorAux ? 'checked' : '') . '>' . ucfirst($valorAux) . '';
        }
        return $html . $mensaje . '</div>';
    }

    function generaSubmit(){
        return '<button type="submit" class = "btnEnviar">Enviar</button>';
    }

    function generaOpcion($nombre, $valor){
        return '<button name = "btnEnviar" type="submit" class = "btnEnviar" value = "'.$valor.'">'.$nombre.'</button>';
    }

    function generaHidden($nombre, $valor){
        return '<input type="hidden" name="'.$nombre.'" value = "'.$valor.'">';
    }

    function generaDate($nameHtml, $nombreCampo, $esValorCorrecto, $valor, $fechaMax){
        $mensaje = !$esValorCorrecto ? '<span style="color:red">Campo obligatorio</span>' : '';
        return '<div class="input-container">
            <label for="'.$nameHtml.'">'.ucfirst($nombreCampo).'</label>
            <input type="date" name="'.$nameHtml.'" id="'.$nameHtml.'" value = "'.$valor.'" max = "'.$fechaMax.'">
            '.$mensaje.'</div>';
    }

    function generaParrafo($enunciado, $valor){
        return '<div class="input-container">
            <p class = "texto">'.$enunciado.': '.$valor.'</p>
            </div>';
    }

    function generaSeleccion($nameHtml, $lista){
        $selectMultiple = '<div class="input-container">
        <label for="'.$nameHtml.'">OPcion</label>
        <select  name="'.$nameHtml.'" id="'.$nameHtml.'">';
            foreach ($lista as $valorAux) {
                $selectMultiple .= '<option value="' . $valorAux . '" ' . (in_array($valorAux, $lista) ? 'selected' : '') . '>' . ucfirst($valorAux) . '</option>';
            }
        return $selectMultiple;
    }
?>
