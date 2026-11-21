package jsoftware.com.jblue.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class FuncUC {

    private static final DateTimeFormatter BASE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

    /**
     * Genera un identificador único concatenando el prefijo, el ID del comité,
     * el ID de la oficina (con formato dinámico de ceros a la izquierda) y la
     * fecha y hora formateada como ddMMyyyyHHmmss.
     *
     * @param prefix Prefijo opcional (ej. "PAG-")
     * @param committeeId ID del comité (ej. 1)
     * @param officeId ID de la oficina o sucursal (ej. 12)
     * @param dateTime Instancia de LocalDateTime a formatear
     * @param secuencia
     * @return Cadena concatenada resultante.
     */
    public static String generateFormattedId(int type, String prefix, int committeeId, int officeId, LocalDateTime dateTime, int secuencia) {
        String safePrefix;
        String formattedcommittee;
        String formattedOffice;
        String formattedDateTime;
        String formattedSequence;
        String final_seq = null;
        switch (type) {
            case 1 -> {
                // 1. Sanitizar el prefijo para evitar nulos literales
                safePrefix = Objects.toString(prefix, "XXX");
                // 2. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedcommittee = (committeeId < 1000) ? String.format("%04d", officeId) : String.format("%08d", officeId);
                // 3. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedOffice = (officeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);
                // 4. Formatear la fecha y hora (ddMMyyyyHHmmss) 14
                formattedDateTime = (dateTime != null) ? dateTime.format(BASE_FORMATTER) : "";
                // 3. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedSequence = (officeId < 1_000_000) ? String.format("%05d", officeId) : String.format("%08d", officeId);
                // 4. Concatenar: prefix + committee_id + office + fecha + secuencia = 30 caracteres
                final_seq = safePrefix + formattedcommittee + formattedOffice + formattedDateTime + formattedSequence;
            }
            case 2 -> {
                // 1. Sanitizar el prefijo para evitar nulos literales
                safePrefix = Objects.toString(prefix, "XX");
                // 2. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedcommittee = (committeeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);
                // 3. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedOffice = (officeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);
                // 4. Formatear la fecha y hora (ddMMyyyyHHmmss)
                formattedDateTime = (dateTime != null) ? dateTime.format(BASE_FORMATTER) : "";
                // 3. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedSequence = (officeId < 1000000) ? String.format("%06d", officeId) : String.format("%08d", officeId);
                // 4. Concatenar: office + secuencia = 15 caracteres
                final_seq = safePrefix + formattedcommittee + formattedOffice + formattedDateTime + formattedSequence;
            }
            case 3 -> {
                // 1. Sanitizar el prefijo para evitar nulos literales
                safePrefix = Objects.toString(prefix, "XX");
                // 2. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedcommittee = (committeeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);
                // 3. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedOffice = (officeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);
                // 4. Formatear la fecha y hora (ddMMyyyyHHmmss)
                formattedDateTime = (dateTime != null) ? dateTime.format(BASE_FORMATTER) : "";
                // 3. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
                formattedSequence = (officeId < 1000000) ? String.format("%06d", officeId) : String.format("%08d", officeId);
                // 4. Concatenar:  office + committee_id + fecha + secuencia = 30 caracteres
                final_seq = safePrefix + formattedcommittee + formattedOffice + formattedDateTime + formattedSequence;
            }
            default ->
                final_seq = null;
        }
        return final_seq;
    }

    /**
     * Genera un identificador único concatenando el prefijo, el ID del comité,
     * el ID de la oficina (con formato dinámico de ceros a la izquierda) y la
     * fecha y hora formateada como ddMMyyyyHHmmss. TODAS LA SECUENCIAS
     * EMPEZARAN CON UN 2 YA QUE ES EL ID DEL AMBIENTE ACTUAL
     *
     * @param committeeId - ID DEL COMITE QUE GENERA EL FOLIO
     * @param officeId - ID DE LA OFICINA QUE GENERA EL FOLIO
     * @param secuencia - SECUENCIA UNICA
     * @return UN FOLIO DE
     */
    public static String getSequenceForWaterIntake(int committeeId, int officeId, int secuencia) {
        return generateFormattedId(1, "AP2", committeeId, officeId, LocalDateTime.now(), secuencia);
    }

    /**
     * Genera un identificador único concatenando el prefijo, el ID del comité,
     * el ID de la oficina (con formato dinámico de ceros a la izquierda) y la
     * fecha y hora formateada como ddMMyyyyHHmmss. TODAS LA SECUENCIAS
     * EMPEZARAN CON UN 2 YA QUE ES EL ID DEL AMBIENTE ACTUAL
     *
     * @param committeeId - ID DEL COMITE QUE GENERA EL FOLIO
     * @param officeId - ID DE LA OFICINA QUE GENERA EL FOLIO
     * @param secuencia - SECUENCIA UNICA
     * @return UN FOLIO DE
     */
    public static String getSequenceForProcess(int committeeId, int officeId, int secuencia) {
        return generateFormattedId(2, null, -1, officeId, null, secuencia);
    }

    /**
     * Genera un identificador único concatenando el prefijo, el ID del comité,
     * el ID de la oficina (con formato dinámico de ceros a la izquierda) y la
     * fecha y hora formateada como ddMMyyyyHHmmss. TODAS LA SECUENCIAS
     * EMPEZARAN CON UN 2 YA QUE ES EL ID DEL AMBIENTE ACTUAL
     *
     * @param committeeId - ID DEL COMITE QUE GENERA EL FOLIO
     * @param officeId - ID DE LA OFICINA QUE GENERA EL FOLIO
     * @param secuencia - SECUENCIA UNICA
     * @return UN FOLIO DE
     */
    public static String getSequenceForPaymentLine(int committeeId, int officeId, int secuencia) {
        return generateFormattedId(3, null, committeeId, officeId, null, secuencia);
    }

    /**
     * Genera un UUID estándar en formato String.
     *
     * @return UN UUID COMO STRING
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(getSequenceForPaymentLine(3, 1, 1));
        System.out.println(getSequenceForProcess(3, 1, 1));
        System.out.println(getSequenceForWaterIntake(3, 1, 1));

        System.out.println(getSequenceForPaymentLine(3, 1, 1).length());
        System.out.println(getSequenceForProcess(3, 1, 1).length());
        System.out.println(getSequenceForWaterIntake(3, 1, 1).length());
    }
}
