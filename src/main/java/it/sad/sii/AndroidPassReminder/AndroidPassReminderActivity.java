package it.sad.sii.AndroidPassReminder;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AndroidPassReminderActivity extends FragmentActivity implements GetInfoAsyncTask.GetInfoListener {

    private String cnumber;
    private String name = "";
    private String expiration;
    private String cardid;
    private String ctype;
    private TextView txtName;
    private PersonalInfo infos;
    private ProgressDialog spinningWheel;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean evenEmpty = false;
        // Get the intent that started this activity
        Intent intent = getIntent();
        Uri data = intent.getData();
        // Figure out what to do based on the intent type
        if (data == null || !data.getScheme().equals("sii")) {
            this.finish();
            return;
        }

        Set<String> parameterNames = data.getQueryParameterNames();
        for (String parameterName : parameterNames) {
            String value = data.getQueryParameter(parameterName);

            if (parameterName.equals("cardid")) {
                cardid = value;
            } else if (parameterName.equals("ctype")) {
                ctype = value;
            } else if (parameterName.equals("cnumber")) {
                cnumber = value;
            } else if (parameterName.equals("firstName")) {
                name = value + " " + name;
            } else if (parameterName.equals("lastName")) {
                name = name + " " + value;
            } else if (parameterName.equals("evenEmpty")) {
                evenEmpty = (value.equals("1"));
            } else if (parameterName.equals("expiration")) {
                expiration = value;
            }
        }

        if (cardid == null || ctype == null || cardid.isEmpty() || ctype.isEmpty()) {
            this.finish();
            return;
        }

        if (!evenEmpty) {
            List<Reminder> reminders = DBAccess.getInstance(this).getReminders(cardid);
            if (reminders.size() == 0) {
                this.finish();
                return;
            }
        }


        setContentView(R.layout.main);
        getWindow().setBackgroundDrawableResource(R.drawable.backlanrep);
        txtName = (TextView) findViewById(R.id.txtName);
        refreshPersonalInfo();

        final Button save = (Button) findViewById(R.id.save);
        final Button info = (Button) findViewById(R.id.info);
        if (cnumber.isEmpty())
            info.setVisibility(View.GONE);


        refreshDbContent();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int selected = radioGroup.getCheckedRadioButtonId();

                int causalId = 0;
                if (selected == R.id.radioFined) {
                    causalId = 1;
                } else if (selected == R.id.radioNoValidate) {
                    causalId = 2;
                } else if (selected == R.id.radioMissingOption) {
                    causalId = 3;
                } else if (selected == R.id.radioDoubleCheckIn) {
                    causalId = 4;
                }

                Reminder reminder = new Reminder(new Date().getTime(), cnumber, name, causalId, cardid, ctype);
                DBAccess.getInstance(AndroidPassReminderActivity.this).insertReminder(reminder);

                refreshDbContent();
            }
        });

        if (!cnumber.isEmpty()) {
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinningWheel = new ProgressDialog(AndroidPassReminderActivity.this);
                    spinningWheel.setMessage(getResources().getString(R.string.spinningMsg));
                    spinningWheel.setCancelable(false);
                    spinningWheel.show();
                    String username = getResources().getString(R.string.api_username);
                    String password = getResources().getString(R.string.api_password);
                    GetInfoAsyncTask task = new GetInfoAsyncTask(AndroidPassReminderActivity.this,
                                                                 cnumber, username, password);
                    task.execute();
                }
            });
        }

    }

    private void refreshDbContent() {
        TextView dbContent = (TextView) findViewById(R.id.dbContent);

        List<Reminder> reminders = DBAccess.getInstance(this).getReminders(cardid);

        String dbContentText = "";
        for (Reminder reminder : reminders) {
            dbContentText += reminder + "\n";
        }
        dbContent.setText(dbContentText);

    }

    private void refreshPersonalInfo() {
        String cardData =
                getResources().getString(R.string.cardid) + ": " + cardid + "\n" +
                        getResources().getString(R.string.ctype) + ": " + ctype;

        if (!name.trim().isEmpty())
            cardData += "\n" + getString(R.string.nome) + ": " + name;
        if (!cnumber.isEmpty())
            cardData += "\n" + getString(R.string.numero_tessera) + ": " + cnumber;
        if (!expiration.isEmpty())
            cardData += "\n" + getString(R.string.expiration) + ": " + expiration;


        txtName.setText(cardData);
    }

    private void showPersonalDataDialog() {
        DialogFragment newFragment = new PersonalDialogFragment();
        Bundle arg = new Bundle();
        arg.putSerializable("infos", infos);
        newFragment.setArguments(arg);
        newFragment.show(getSupportFragmentManager(), "personal_info");
    }


    public void onError(String error){
        if (spinningWheel != null) {
            spinningWheel.dismiss();
            spinningWheel = null;
        }
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void onPersonalInfoReceived(String data) {
        if (spinningWheel != null) {
            spinningWheel.dismiss();
            spinningWheel = null;
        }
        JSONObject jsonValues;
        try {
            infos = new PersonalInfo();
            jsonValues = new JSONObject(data).getJSONObject("values");
            JSONObject jsonTicketStatus = jsonValues.optJSONObject("ticket_status");
            if (jsonTicketStatus != null) {
                Integer ticketCode = jsonTicketStatus.getInt("code");
                String ticketDescIt = jsonTicketStatus.getString("description_it");
                String ticketDescDe = jsonTicketStatus.getString("description_de");
                infos.setTicketCode(ticketCode);
                infos.setTicketDescIt(ticketDescIt);
                infos.setTicketDescDe(ticketDescDe);
            }
            JSONObject jsonPersonal = jsonValues.getJSONObject("personal");
            String surname = jsonPersonal.getString("surname");
            String name = jsonPersonal.getString("name");
            String codfisc = jsonPersonal.getString("codfisc");
            infos.setSurname(surname);
            infos.setName(name);
            infos.setCodfisc(codfisc);
            JSONObject jsonContacts = jsonValues.getJSONObject("contacts");
            String email = jsonContacts.getString("email");
            String phone = jsonContacts.getString("phone");
            String mobile = jsonContacts.getString("mobile");
            String fax = jsonContacts.getString("fax");
            infos.setEmail(email);
            infos.setPhone(phone);
            infos.setMobile(mobile);
            infos.setFax(fax);
            JSONObject jsonAddress = jsonValues.getJSONObject("address");
            String street = jsonAddress.getString("street");
            String street2 = jsonAddress.getString("street2");
            String location = jsonAddress.getString("location");
            infos.setStreet(street);
            infos.setStreet2(street2);
            infos.setLocation(location);
            JSONObject jsonTown = jsonAddress.getJSONObject("town");
            String townNameIt = jsonTown.getString("name_it");
            String townNameDe = jsonTown.getString("name_de");
            String district = jsonTown.getString("district");
            String zip = jsonTown.getString("zip");
            infos.setTownNameIt(townNameIt);
            infos.setTownNameDe(townNameDe);
            infos.setDistrict(district);
            infos.setZip(zip);
            JSONObject jsonCountry = jsonAddress.getJSONObject("country");
            String countryNameIt = jsonCountry.getString("name_it");
            String countryNameDe = jsonCountry.getString("name_de");
            String code = jsonCountry.getString("code");
            Boolean eu = jsonCountry.getBoolean("EU");
            infos.setCountryNameIt(countryNameIt);
            infos.setCountryNameDe(countryNameDe);
            infos.setCode(code);
            infos.setEu(eu);
            showPersonalDataDialog();
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.getMessage());
        }
    }

}
