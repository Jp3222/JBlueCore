package jsoftware.com.jblue.model.abst;

import jsoftware.com.jblue.model.exp.ServiceException;

/**
 * Contrato maestro para la gestión de operaciones criptográficas en el sistema.
 * Define las acciones de cifrado, descifrado y comparación de datos.
 *
 * * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-06-11
 * @version 1.0
 */
public interface CryptoEngineModel {

    // Variables constantes de la interfaz (públicas, estáticas y finales por definición)
    int ENCRYP = 1;
    int DECRYP = 2;

    /**
     * Aplica un algoritmo de cifrado a la cadena de texto plano proporcionada.
     *
     * @param datos Cadena de texto original a encriptar.
     * @param claveSecreta Clave maestra o semilla para la operación.
     * @return El texto cifrado (usualmente codificado en Base64 o Hexadecimal).
     * @throws ServiceException Si ocurre un fallo en las dependencias
     * criptográficas.
     */
    String encryp(String datos, String claveSecreta) throws ServiceException;

    /**
     * Revierte el cifrado de una cadena para recuperar el texto original.
     *
     * @param datosEncriptados Cadena de texto cifrada.
     * @param claveSecreta Clave maestra utilizada para la operación.
     * @return El texto plano recuperado.
     * @throws ServiceException Si los datos están corruptos o la clave es
     * incorrecta.
     */
    String decryp(String datosEncriptados, String claveSecreta) throws ServiceException;

    /**
     * Compara un texto plano contra un texto ya cifrado evaluando su
     * equivalencia en modo encriptación. Útil para verificar datos sin exponer
     * la información original.
     *
     * @param textoPlano Texto de entrada que se desea validar.
     * @param textoCifrado Base de comparación cifrada.
     * @param claveSecreta Clave para el procesamiento.
     * @return true si los contenidos coinciden sustancialmente; de lo
     * contrario, false.
     * @throws ServiceException Si ocurre un error durante el cálculo de
     * equivalencia.
     */
    boolean equalsEncryp(String textoPlano, String textoCifrado, String claveSecreta) throws ServiceException;

    /**
     * Compara un texto cifrado contra una cadena de texto plano tras realizar
     * una operación de descifrado.
     *
     * @param textoCifrado Contenido protegido que se va a descifrar para la
     * comparación.
     * @param textoPlano Objetivo de comparación en texto legible.
     * @param claveSecreta Clave de descifrado.
     * @return true si el texto descifrado es idéntico al texto plano; de lo
     * contrario, false.
     * @throws ServiceException Si ocurre un error en el flujo de
     * desencriptación.
     */
    boolean equalsDecryp(String textoCifrado, String textoPlano, String claveSecreta) throws ServiceException;

    /**
     * Ejecuta una acción de control o cambia el estado operacional del motor
     * criptográfico. Puede utilizarse en conjunto con las constantes
     * {@link #ENCRYP} o {@link #DECRYP}.
     *
     * @param mode Indicador entero del modo de operación (e.g., 1 para ENCRYP,
     * 2 para DECRYP).
     * @throws ServiceException Si el modo enviado no es soportado por la
     * implementación.
     */
    void doAction(int mode) throws ServiceException;
}
