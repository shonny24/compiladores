package co.edu.uniquindio.compiladores.proyecto.controladores

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class InicioController : Initializable {

    @FXML
    lateinit var codigoFuente: TextArea
    @FXML
    lateinit var tablaTokens: TableView<Token>
    @FXML
    lateinit var colLexema: TableColumn<Token, String>
    @FXML
    lateinit var colCategoria: TableColumn<Token, String>
    @FXML
    lateinit var colFila: TableColumn<Token, Int>
    @FXML
    lateinit var colColumna: TableColumn<Token, Int>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        colLexema.cellValueFactory = PropertyValueFactory("lexema")
        colCategoria.cellValueFactory = PropertyValueFactory("categoria")
        colFila.cellValueFactory = PropertyValueFactory("fila")
        colColumna.cellValueFactory = PropertyValueFactory("columna")

    }


    @FXML
    fun analizarCodigo(e: ActionEvent) {

        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()


            tablaTokens.items=FXCollections.observableArrayList(lexico.listaTokens)
        }

    }


}