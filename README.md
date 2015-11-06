# AndroidPassReminder

AndroidPassReminder is a sample project to test and show the new functionalities introduced with version 2.4.6 of SPASSDroid.

SPASSDroid (the SII application for ticket inspectors, used to verify if a smartcard has been correctly validated) now can be integrated with 3rd party Android applications, through the standard mechanism of [Intents](http://developer.android.com/reference/android/content/Intent.html).

The first intent is fired every time a smartcard (a Tag, in Andorid) is sucessfully read. This intent, for privacy reasons (i.e. to avoid automatic personal data collection) contains only the `cardid` (the Mifare UID) and the `ctype` (the SII card type), which may be one of:
  - 70 Abo65
  - 73/74 Pass Free
  - 79 Abo+
  - 80 Pass
  - 83/84/85 Mobilcard

The second intent is fired when the Card Number (under the "Card" tab) is pressed.
This second intent contains more information (more or less, all the information contained in the read-only portion of the smartcard memory). In particular:
- `cnumber`: the card number
- `firstName`, `lastName`: the user name and surname
- `expiration`: the card expiration date

Both Intents follow the following pattern:
 - schema: sii
 - host: passData
 - query parameters: the parameters listed above

The Intent looks like: `sii://passData?cardid=...&ctype=...&...`

To intercept these Intents, you just set up an intent-filter in your AndroidManifest.xml:

    <intent-filter>
       <action android:name="android.intent.action.VIEW"/>
       <category android:name="android.intent.category.DEFAULT"/>
       <category android:name="android.intent.category.BROWSABLE"/>
       <data
           android:scheme="sii"
           android:host="passData"/>
    </intent-filter>
    
Then, to extract data, you process the intent in the onCreate method of the activity you set up the intent filter for:

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            // Process parameters....

See AndroidManifest.xml and AndroidPassReminderActivity.java for details

### Get personal info

This example app shows another functionality we are deploying: an API to get info about the contract holder of a particular card. 

This API si published on our public server, under the address `https://www.sii.bz.it/api/altoadigepass/tickets/`

In particular, using the cnumber as an ID, it is possible to request address and contact information about the contract holder of the card, calling the "personal" action:
`https://www.sii.bz.it/api/altoadigepass/tickets/{cnumber}/personal`

Obviously, this API is not publicly accessible; it is password and firewall (IP) protected. If you want to get access to this API, you have to contact the  `developer-support` email at ServiziST.

Notice that the API is accessed directly from the application here only as an example! Ideally, the structure would involve a server-to-server communication:

```
SPASSDroid -(Intent)-> App 
                        |
                        -> AppServer -(https) -> SII Server
```
The response is a JSON message (application/json) with the structure described in the "SPASS Server documentation" (available upon request at `developer-support`).

### The app

The app uses a SQLite database (provided by Android) to store relevant events ("reminders") about a card, so that the ticket inspector can use it to remember a particular behaviour or violation.

This information is stored when the ticket inspector fires the app through the second activity (tapping the *Card number* field), using the cardid as a key. Then, it can be recalled by the first event (as a "reminder", or a warning, when the inspector is checking a smartcard). 

The app is only an example, so there is little to no error checking, but it shows what can be done by handling these two simple Intents.

#### Usage

The app can be used as-is, but it will not connect to our server, since there are no credentials for the api.
In order to make it work, you have to:
- contact SII (`developer-support`) to obtain a set of credentials. Do not forget to include your IP!
- create an `api_access.xml` file inside the `res/values` folder with the following content:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="api_username">YOUR_USERNAME</string>
    <string name="api_password">YOUR_PASSWORD</string>
</resources>
```

### License

MIT

