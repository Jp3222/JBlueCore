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
     * @return Cadena concatenada resultante.
     */
    public static String generateFormattedId(String prefix, int committeeId, int officeId, LocalDateTime dateTime) {
        // 1. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
        String formattedcommittee = (committeeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);

        // 1. Formatear la oficina (4 dígitos si es menor a 10000, de lo contrario 8 dígitos)
        String formattedOffice = (officeId < 10000) ? String.format("%04d", officeId) : String.format("%08d", officeId);

        // 2. Formatear la fecha y hora (ddMMyyyyHHmmss)
        String formattedDateTime = (dateTime != null) ? dateTime.format(BASE_FORMATTER) : "";

        // 3. Sanitizar el prefijo para evitar nulos literales
        String safePrefix = Objects.toString(prefix, "XX");

        // 4. Concatenar: prefix + committee_id + office + fecha
        return safePrefix + formattedcommittee + formattedOffice + formattedDateTime;
    }

    /**
     * Genera un UUID estándar en formato String.
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
