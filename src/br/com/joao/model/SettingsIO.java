package br.com.joao.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.KXml2Driver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class SettingsIO {

    public static final String UTF8 = "UTF-8";
    public static final String NEW_LINE = "\n";

    /**
     * Reads a file with settings paramaters.
     *
     * @param file File to read
     * @return A settings object from file.
     * @throws FileNotFoundException File not exists or can't be readed.
     */
    public static Settings readSettingsFile(final File file) throws FileNotFoundException {
        if (file == null || !file.canRead()) {
            throw new FileNotFoundException("Can't find or read " + file);
        }

        XStream xstream = getXstreamObject();

        Settings settings = (Settings) xstream.fromXML(file);
        return settings;
    }

    /**
     * Writes into the settings objects in a string with xml format.
     *
     * @param file File to write the settings.
     * @param settings Settings to write.
     * @throws java.lang.Exception IOException
     */
    public static void writeSettings(final File file, final Settings settings) throws Exception {
        XStream xstream = getXstreamObject();

        final StringBuilder sb = new StringBuilder();
        sb.append(xstream.toXML(settings)).append(NEW_LINE);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes(UTF8));
        } catch (final IOException ex) { // catch only to close file, on finally
            throw ex;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
    }

    private static XStream getXstreamObject() {
        XStream xstream = new XStream(new KXml2Driver());
        xstream.processAnnotations(Settings.class);

        xstream.ignoreUnknownElements();

        xstream.setMode(XStream.ID_REFERENCES);

        return xstream;
    }
}
