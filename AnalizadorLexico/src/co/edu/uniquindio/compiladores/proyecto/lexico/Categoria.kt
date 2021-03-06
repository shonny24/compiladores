package co.edu.uniquindio.compiladores.proyecto.lexico

enum class Categoria {
    ENTERO,DECIMAL, CADENA, CARACTER, OPERADOR_ARITMETICO, OPERADOR_INCREMENTO, OPERADOR_DECREMENTO,
    OPERADOR_ASIGNACION, OPERADOR_RELACIONAL, OPERADOR_LOGICO, NO_RECONOCIDO,
    PALABRA_RESERVADA_CLASE, PALABRA_RESERVADA_RETORNO,
    PALABRA_RESERVADA_DESCISIONES, FIN_SENTENCIA, SEPARADORES, COMENTARIO_BLOQUE, BLOQUE_AGRUPACION_PARENTESIS,
    BLOQUE_AGRUPACION_LLAVES, BLOQUE_AGRUPACION_CORCHETES, COMENTARIO_LINEA,PARENTESIS_IZQUIERDO,
    PARENTESIS_DERECHO, LLAVE_DERECHO, LLAVE_IZQUIERDO, CORCHETE_DERECHO, CORCHETE_IZQUIERDO
}