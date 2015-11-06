package it.sad.sii.AndroidPassReminder;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mmutschl on 18/08/15.
 */
public class Reminder {
    private long checktime;
    private String cnumber;
    private String cardid;
    private String ctype;
    private String name;
    private int causalId;

    private Map<Integer, String> causalText;

    public Reminder(long checktime, String cnumber, String name, int causalId, String cardid, String ctype) {
        this.checktime = checktime;
        this.causalId = causalId;
        this.name = name;
        this.cnumber = cnumber;
        this.cardid = cardid;
        this.ctype = ctype;
        causalText = new HashMap<Integer, String>();

        if (Locale.getDefault().getLanguage().equals("de")) {
            causalText.put(1, "Strafe");
            causalText.put(2, "Nicht entwertet");
            causalText.put(3, "Option fehlt");
            causalText.put(4, "Doppelcheck-in");

        } else {
            causalText.put(1, "Multato");
            causalText.put(2, "Non obliterato");
            causalText.put(3, "Senza opzioni");
            causalText.put(4, "Doppio check-in");
        }
    }

    public long getChecktime() {
        return checktime;
    }

    public void setChecktime(long checktime) {
        this.checktime = checktime;
    }

    public String getCnumber() {
        return cnumber;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCausalId() {
        return causalId;
    }

    public void setCausalId(int causalId) {
        this.causalId = causalId;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd/MM/yy hh:mm");
        return dateFormatter.print(checktime) + " - " + causalText.get(causalId);
    }
}
