package it.sad.sii.AndroidPassReminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by lconcli on 20/10/15.
 */
public class PersonalDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        PersonalInfo infos = (PersonalInfo) getArguments().getSerializable("infos");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.personal_info_dialog, null);
        builder.setView(view)
                // Add action buttons
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PersonalDialogFragment.this.getDialog().hide();
                    }
                });

        TextView dialog_txtTicket = (TextView) view.findViewById(R.id.dialog_txtTicket);
        TextView dialog_txtName = (TextView) view.findViewById(R.id.dialog_txtName);
        TextView dialog_txtCodFisc = (TextView) view.findViewById(R.id.dialog_txtCodFisc);
        TextView dialog_txtEmail = (TextView) view.findViewById(R.id.dialog_txtEmail);
        TextView dialog_txtPhone = (TextView) view.findViewById(R.id.dialog_txtPhone);
        TextView dialog_txtMobile = (TextView) view.findViewById(R.id.dialog_txtMobile);
        TextView dialog_txtFax = (TextView) view.findViewById(R.id.dialog_txtFax);
        TextView dialog_txtStreet = (TextView) view.findViewById(R.id.dialog_txtStreet);
        TextView dialog_txtLocation = (TextView) view.findViewById(R.id.dialog_txtLocation);
        TextView dialog_txtTown = (TextView) view.findViewById(R.id.dialog_txtTown);
        TextView dialog_txtDistrict = (TextView) view.findViewById(R.id.dialog_txtDistrict);
        TextView dialog_txtZip = (TextView) view.findViewById(R.id.dialog_txtZip);
        TextView dialog_txtCountry = (TextView) view.findViewById(R.id.dialog_txtCountry);
        ImageView dialog_imgFlag = (ImageView) view.findViewById(R.id.dialog_imgFlag);


        TextView dialog_label_name = (TextView) view.findViewById(R.id.dialog_label_name);
        TextView dialog_label_codfisc = (TextView) view.findViewById(R.id.dialog_label_codfisc);
        TextView dialog_label_email = (TextView) view.findViewById(R.id.dialog_label_email);
        TextView dialog_label_phone = (TextView) view.findViewById(R.id.dialog_label_phone);
        TextView dialog_label_mobile = (TextView) view.findViewById(R.id.dialog_label_mobile);
        TextView dialog_label_fax = (TextView) view.findViewById(R.id.dialog_label_fax);
        TextView dialog_label_street = (TextView) view.findViewById(R.id.dialog_label_street);
        TextView dialog_label_location = (TextView) view.findViewById(R.id.dialog_label_location);
        TextView dialog_label_town = (TextView) view.findViewById(R.id.dialog_label_town);
        TextView dialog_label_district = (TextView) view.findViewById(R.id.dialog_label_district);
        TextView dialog_label_zip = (TextView) view.findViewById(R.id.dialog_label_zip);
        TextView dialog_label_country = (TextView) view.findViewById(R.id.dialog_label_country);


        View hLine = view.findViewById(R.id.hLine);
        hLine.setBackgroundColor(dialog_label_codfisc.getTextColors().getDefaultColor());

        if (Locale.getDefault().getLanguage().equals("de")) {
            dialog_txtTicket.setText(infos.getTicketCode() + " - " + infos.getTicketDescDe());
        } else {
            dialog_txtTicket.setText(infos.getTicketCode() + " - " + infos.getTicketDescIt());
        }

        if ((infos.getSurname() != null && !infos.getSurname().isEmpty())
                || (infos.getName() != null && !infos.getName().isEmpty())) {
            dialog_txtName.setText(infos.getSurname() + infos.getName());
        } else {
            dialog_label_name.setVisibility(View.GONE);
            dialog_txtName.setVisibility(View.GONE);
        }

        if (infos.getCodfisc() != null && !infos.getCodfisc().isEmpty()) {
            dialog_txtCodFisc.setText(infos.getCodfisc());
        } else {
            dialog_label_codfisc.setVisibility(View.GONE);
            dialog_txtCodFisc.setVisibility(View.GONE);
        }

        if (infos.getEmail() != null && !infos.getEmail().isEmpty()) {
            dialog_txtEmail.setText(infos.getEmail());
        } else {
            dialog_label_email.setVisibility(View.GONE);
            dialog_txtEmail.setVisibility(View.GONE);
        }

        if (infos.getPhone() != null && !infos.getPhone().isEmpty()) {
            dialog_txtPhone.setText(infos.getPhone());
        } else {
            dialog_label_phone.setVisibility(View.GONE);
            dialog_txtPhone.setVisibility(View.GONE);
        }


        if (infos.getMobile() != null && !infos.getMobile().isEmpty()) {
            dialog_txtMobile.setText(infos.getMobile());
        } else {
            dialog_label_mobile.setVisibility(View.GONE);
            dialog_txtMobile.setVisibility(View.GONE);
        }


        if (infos.getFax() != null && !infos.getFax().isEmpty()) {
            dialog_txtFax.setText(infos.getFax());
        } else {
            dialog_label_fax.setVisibility(View.GONE);
            dialog_txtFax.setVisibility(View.GONE);
        }


        if ((infos.getStreet() != null && !infos.getStreet().isEmpty())
                || (infos.getStreet2() != null && !infos.getStreet2().isEmpty())) {
            dialog_txtStreet.setText(infos.getStreet() + infos.getStreet2());
        } else {
            dialog_label_street.setVisibility(View.GONE);
            dialog_txtStreet.setVisibility(View.GONE);
        }


        if (infos.getLocation() != null && !infos.getLocation().isEmpty()) {
            dialog_txtLocation.setText(infos.getLocation());
        } else {
            dialog_label_location.setVisibility(View.GONE);
            dialog_txtLocation.setVisibility(View.GONE);
        }


        if (Locale.getDefault().getLanguage().equals("de")) {
            if (infos.getTownNameDe() != null && !infos.getTownNameDe().isEmpty()) {
                dialog_txtTown.setText(infos.getTownNameDe());
            } else {
                dialog_label_town.setVisibility(View.GONE);
                dialog_txtTown.setVisibility(View.GONE);
            }
        } else {
            if (infos.getTownNameIt() != null && !infos.getTownNameIt().isEmpty()) {
                dialog_txtTown.setText(infos.getTownNameIt());
            } else {
                dialog_label_town.setVisibility(View.GONE);
                dialog_txtTown.setVisibility(View.GONE);
            }
        }


        if (infos.getDistrict() != null && !infos.getDistrict().isEmpty()) {
            dialog_txtDistrict.setText(infos.getDistrict());
        } else {
            dialog_label_district.setVisibility(View.GONE);
            dialog_txtDistrict.setVisibility(View.GONE);
        }


        if (infos.getZip() != null && !infos.getZip().isEmpty()) {
            dialog_txtZip.setText(infos.getZip());
        } else {
            dialog_label_zip.setVisibility(View.GONE);
            dialog_txtZip.setVisibility(View.GONE);
        }


        if (Locale.getDefault().getLanguage().equals("de")) {
            if ((infos.getCountryNameDe() != null && !infos.getCountryNameDe().isEmpty())) {
                dialog_txtCountry.setText(infos.getCountryNameDe());
            }
        } else {
            if ((infos.getCountryNameIt() != null && !infos.getCountryNameIt().isEmpty())) {
                dialog_txtCountry.setText(infos.getCountryNameIt());
            }
        }

        String countryTxt;
        if (infos.getCode() != null && !infos.getCode().isEmpty()) {
            countryTxt = (String) dialog_txtCountry.getText();
            if (countryTxt != null && !countryTxt.isEmpty()) {
                countryTxt = countryTxt + " ";
            }
            dialog_txtCountry.setText(countryTxt + "(" + infos.getCode() + ")");
        }

        countryTxt = (String) dialog_txtCountry.getText();

        if (countryTxt != null && !countryTxt.isEmpty()) {
            if (infos.getEu() != null && infos.getEu()) {
                dialog_imgFlag.setVisibility(View.VISIBLE);
            } else {
                dialog_imgFlag.setVisibility(View.GONE);
            }
        }else{
            dialog_imgFlag.setVisibility(View.GONE);
            dialog_label_country.setVisibility(View.GONE);
            dialog_txtCountry.setVisibility(View.GONE);
        }
        return builder.create();
    }
}
