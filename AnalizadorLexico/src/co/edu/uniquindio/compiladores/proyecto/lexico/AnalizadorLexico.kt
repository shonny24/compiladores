package co.edu.uniquindio.compiladores.proyecto.lexico

class AnalizadorLexico(var codigoFuente: String) {
    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    //Verifica si el token es un comentario de bloque
    fun esComentarioBloque(): Boolean {
        var lexema = ""
        if (caracterActual == '.') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual != '.') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        while (caracterActual != '.' && caracterActual != finCodigo) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }

                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == '.') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == '.') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(
                                    lexema,
                                    Categoria.COMENTARIO_BLOQUE, filaInicial, columnaInicial
                                )
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }


        }
        //RI
        return false
    }

    fun esEntero(): Boolean {
        if (caracterActual.isDigit()) {
            //Inicialización de variables necesarias para almacenar información
            var lexema = ""
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            val posicionInicial = posicionActual
            //Transición Inicial
            lexema += caracterActual
            obtenerSiguienteCaracter()
            //Bucle
            while (caracterActual.isDigit()) {
                //Transición
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            //Bactracking BT
            if (caracterActual == '.') {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
            //Aceptación y Almacenamiento AA
            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
            return true
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es una cadena
    fun esCadena(): Boolean {
        var lexema = ""
        if (caracterActual == '%') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual != '%' || caracterActual == '^') {
                lexema += caracterActual

                while (caracterActual != '%' || caracterActual == '^') {

                    if (caracterActual != '%') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    } else {
                        if (caracterActual == '^') {
                            obtenerSiguienteCaracter()
                            if (caracterActual == '*' || caracterActual == '?' || caracterActual == ']' || caracterActual == '_') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                }
                if (caracterActual == '%') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.CADENA, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un caracter
    fun esCaracter(): Boolean {
        var lexema = ""
        if (caracterActual == '/') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual != '/') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '/') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.CARACTER, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //verifica si es un token aritmetico
    fun esOperadorAritmetico(): Boolean {
        var lexema = ""
        if (caracterActual == 'S' || caracterActual == 'R' || caracterActual == 'm' || caracterActual == 'd') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '+' || caracterActual == '-' || caracterActual == '#' || caracterActual == '/' || caracterActual == 'o') {
                lexema += caracterActual
                if (caracterActual == '#' || caracterActual == 'o') {
                    if (caracterActual == '#') {
                        obtenerSiguienteCaracter()
                        if (caracterActual != '∞') {
                            almacenarToken(
                                lexema,
                                Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        obtenerSiguienteCaracter()
                        lexema += caracterActual
                        if (caracterActual == 'd') {
                            obtenerSiguienteCaracter()
                            lexema += caracterActual
                            if (caracterActual == '%') {
                                obtenerSiguienteCaracter()
                                if (caracterActual != '∞') {
                                    almacenarToken(
                                        lexema,
                                        Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                                    )
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                } else {
                    obtenerSiguienteCaracter()
                    if (caracterActual == '+' || caracterActual == '-' || caracterActual == '/') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual != '∞') {
                            almacenarToken(
                                lexema,
                                Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //OPERADORES INCREMENTO,DECREMENTO
    fun esOperadorIncrementoDecremento(): Boolean {
        var lexema = ""
        if (caracterActual == '[') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '+' || caracterActual == '-') {
                lexema += caracterActual
                if (caracterActual == '+') {
                    obtenerSiguienteCaracter()
                    if (caracterActual == ']') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else if (caracterActual == '-') {
                    obtenerSiguienteCaracter()
                    if (caracterActual == ']') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un Operador_relacional
    fun esOperadorRelacional(): Boolean {
        var lexema = ""
        if (caracterActual == '∞' || caracterActual == '+' || caracterActual == '-' || caracterActual == '|') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            if (caracterActual == '∞') {
                obtenerSiguienteCaracter()
                almacenarToken(
                    lexema,
                    Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                )
                return true
            } else {
                if (caracterActual == '+' || caracterActual == '-') {
                    obtenerSiguienteCaracter()
                    if (caracterActual == '∞') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        almacenarToken(
                            lexema,
                            Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                        )
                        return true
                    }
                } else {
                    if (caracterActual == '|') {
                        obtenerSiguienteCaracter()
                        if (caracterActual == '∞') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un Operador_Logico
    fun esOperadorLogico(): Boolean {
        var lexema = ""
        if (caracterActual == 'Y' || caracterActual == 'O' || caracterActual == '|') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            if (caracterActual == 'O' || caracterActual == '|') {
                obtenerSiguienteCaracter()
                if (caracterActual != '∞') {
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                obtenerSiguienteCaracter()
                if (caracterActual == 'Y') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema,
                        Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //verifica si es un token de asignacion
    fun esOperadorAsignacion(): Boolean {
        var lexema = ""
        if (caracterActual == 'S' || caracterActual == 'R' || caracterActual == 'm' || caracterActual == 'd') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '+' || caracterActual == '-' || caracterActual == '#' || caracterActual == '/' || caracterActual == 'o') {
                lexema += caracterActual
                if (caracterActual == '#' || caracterActual == 'o') {
                    if (caracterActual == '#') {
                        obtenerSiguienteCaracter()
                        if (caracterActual == '∞') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        obtenerSiguienteCaracter()
                        lexema += caracterActual
                        if (caracterActual == 'd') {
                            obtenerSiguienteCaracter()
                            lexema += caracterActual
                            if (caracterActual == '%') {
                                obtenerSiguienteCaracter()
                                if (caracterActual == '∞') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    almacenarToken(
                                        lexema,
                                        Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                                    )
                                    return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                    return false
                                }
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }
                } else {
                    obtenerSiguienteCaracter()
                    if (caracterActual == '+' || caracterActual == '-' || caracterActual == '/') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == '∞') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un separador
    fun esSeparadorPunto(): Boolean {
        var lexema = ""
        if (caracterActual == '.') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.SEPARADORES, filaInicial, columnaInicial
            )
            return true
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un separador
    fun esSeparadorComa(): Boolean {
        var lexema = ""
        if (caracterActual == ',') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.SEPARADORES, filaInicial, columnaInicial
            )
            return true
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un separador
    fun esSeparadorDosPuntos(): Boolean {
        var lexema = ""
        if (caracterActual == ':') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.SEPARADORES, filaInicial, columnaInicial
            )
            return true
        }
        //Rechazo inmediato RI
        return false
    }


    //Verifica si es un identificador de clase
    fun esPalabraReservadaClase(): Boolean {
        var lexema = ""
        if (caracterActual == 'c') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'l') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'a') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 's') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 's') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_CLASE, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si una palabra reservada condicional
    fun esUnaPalabraReservadaCondicional(): Boolean {
        var lexema = ""
        if (caracterActual == 'Y' || caracterActual == 'N') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            if (caracterActual == 'Y') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'e') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 's') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(
                            lexema,
                            Categoria.PALABRA_RESERVADA_DESCICIONES, filaInicial, columnaInicial
                        )
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                if (caracterActual == 'N') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == 'o') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 't') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_DESCICIONES, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si es la palabra reservada de retorno
    fun esPalabraReservadaRetorno(): Boolean {
        var lexema = ""
        if (caracterActual == '>') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'b') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'a') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'c') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'k') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(
                                lexema,
                                Categoria.PALABRA_RESERVADA_RETORNO, filaInicial, columnaInicial
                            )
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es fin de sentencia
    fun esFinSentencia(): Boolean {
        var lexema = ""
        if (caracterActual == '$') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(
                lexema,
                Categoria.FIN_SENTENCIA, filaInicial, columnaInicial
            )
            return true
        }
        //Rechazo inmediato RI
        return false
    }

    //Verifica si el token es un bloque de Agrupacion
    fun esBloqueAgrupacionParentesis(): Boolean {
        var lexema = ""
        if (caracterActual == '(') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual != ')' && caracterActual != finCodigo) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == ')') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != 'C') {

                    almacenarToken(
                        lexema,
                        Categoria.BLOQUE_AGRUPACION_PARENTESIS, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un bloque de Agrupacion
    fun esBloqueAgrupacionLlaves(): Boolean {
        var lexema = ""
        if (caracterActual == '{') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual != '}' && caracterActual != finCodigo) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == '}') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != 'C') {

                    almacenarToken(
                        lexema,
                        Categoria.BLOQUE_AGRUPACION_LLAVES, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Verifica si el token es un bloque de Agrupacion
    fun esBloqueAgrupacionCorchetes(): Boolean {
        var lexema = ""
        if (caracterActual == '[') {

            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual != ']' && caracterActual != finCodigo) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == ']') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != 'C') {

                    almacenarToken(
                        lexema,
                        Categoria.BLOQUE_AGRUPACION_CORCHETES, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        //RI
        return false
    }

    //Repetir multiples veces la comprobacion de los automatas
    fun analizar() {
        while (caracterActual != finCodigo) {

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }
            if (esBloqueAgrupacionLlaves()) continue
            if (esBloqueAgrupacionParentesis()) continue
            if (esComentarioBloque()) continue
            if (esEntero()) continue
            if (esCadena()) continue
            if (esCaracter()) continue
            if (esSeparadorDosPuntos()) continue
            if (esSeparadorComa()) continue
            if (esSeparadorPunto()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorIncrementoDecremento()) continue
            if (esBloqueAgrupacionCorchetes()) continue
            if (esOperadorRelacional()) continue
            if (esOperadorLogico()) continue
            if (esOperadorAsignacion()) continue
            if (esFinSentencia()) continue
            if (esPalabraReservadaClase()) continue
            if (esUnaPalabraReservadaCondicional()) continue
            if (esPalabraReservadaRetorno()) continue



            almacenarToken(
                "" + caracterActual,
                Categoria.NO_RECONOCIDO, filaActual, columnaActual
            )
            obtenerSiguienteCaracter()

        }

    }


    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
        listaTokens.add(Token(lexema, categoria, fila, columna))

    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int) {
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }
}