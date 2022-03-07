package android.provider;

public static final class Intents
{
    public static final String ACTION_VOICE_SEND_MESSAGE_TO_CONTACTS = "android.provider.action.VOICE_SEND_MESSAGE_TO_CONTACTS";
    public static final String ATTACH_IMAGE = "com.android.contacts.action.ATTACH_IMAGE";
    public static final String CONTACTS_DATABASE_CREATED = "android.provider.Contacts.DATABASE_CREATED";
    public static final String EXTRA_CREATE_DESCRIPTION = "com.android.contacts.action.CREATE_DESCRIPTION";
    public static final String EXTRA_FORCE_CREATE = "com.android.contacts.action.FORCE_CREATE";
    public static final String EXTRA_RECIPIENT_CONTACT_CHAT_ID = "android.provider.extra.RECIPIENT_CONTACT_CHAT_ID";
    public static final String EXTRA_RECIPIENT_CONTACT_NAME = "android.provider.extra.RECIPIENT_CONTACT_NAME";
    public static final String EXTRA_RECIPIENT_CONTACT_URI = "android.provider.extra.RECIPIENT_CONTACT_URI";
    public static final String INVITE_CONTACT = "com.android.contacts.action.INVITE_CONTACT";
    public static final String METADATA_ACCOUNT_TYPE = "android.provider.account_type";
    public static final String METADATA_MIMETYPE = "android.provider.mimetype";
    public static final String SEARCH_SUGGESTION_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CLICKED";
    public static final String SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED";
    public static final String SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED";
    public static final String SHOW_OR_CREATE_CONTACT = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT";
    
    public Intents() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Insert
    {
        public static final String ACTION = "android.intent.action.INSERT";
        public static final String COMPANY = "company";
        public static final String DATA = "data";
        public static final String EMAIL = "email";
        public static final String EMAIL_ISPRIMARY = "email_isprimary";
        public static final String EMAIL_TYPE = "email_type";
        public static final String EXTRA_ACCOUNT = "android.provider.extra.ACCOUNT";
        public static final String EXTRA_DATA_SET = "android.provider.extra.DATA_SET";
        public static final String FULL_MODE = "full_mode";
        public static final String IM_HANDLE = "im_handle";
        public static final String IM_ISPRIMARY = "im_isprimary";
        public static final String IM_PROTOCOL = "im_protocol";
        public static final String JOB_TITLE = "job_title";
        public static final String NAME = "name";
        public static final String NOTES = "notes";
        public static final String PHONE = "phone";
        public static final String PHONETIC_NAME = "phonetic_name";
        public static final String PHONE_ISPRIMARY = "phone_isprimary";
        public static final String PHONE_TYPE = "phone_type";
        public static final String POSTAL = "postal";
        public static final String POSTAL_ISPRIMARY = "postal_isprimary";
        public static final String POSTAL_TYPE = "postal_type";
        public static final String SECONDARY_EMAIL = "secondary_email";
        public static final String SECONDARY_EMAIL_TYPE = "secondary_email_type";
        public static final String SECONDARY_PHONE = "secondary_phone";
        public static final String SECONDARY_PHONE_TYPE = "secondary_phone_type";
        public static final String TERTIARY_EMAIL = "tertiary_email";
        public static final String TERTIARY_EMAIL_TYPE = "tertiary_email_type";
        public static final String TERTIARY_PHONE = "tertiary_phone";
        public static final String TERTIARY_PHONE_TYPE = "tertiary_phone_type";
        
        public Insert() {
            throw new RuntimeException("Stub!");
        }
    }
}
