package co.edu.uniquindio.compiladores.proyecto.lexico

enum class Categoria {
    ENTERO, CADENA, CARACTER, OPERADOR_ARITMETICO, OPERADOR_INCREMENTO, OPERADOR_DECREMENTO,
    OPERADOR_ASIGNACION, OPERADOR_RELACIONAL, OPERADOR_LOGICO, NO_RECONOCIDO,
    PALABRA_RESERVADA_CLASE, PALABRA_RESERVADA_RETORNO,
    PALABRA_RESERVADA_DESCICIONES,FIN_SENTENCIA, SEPARADORES
}