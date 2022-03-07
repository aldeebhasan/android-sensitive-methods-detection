package android.provider;

import android.net.*;
import android.content.res.*;

public static final class CommonDataKinds
{
    CommonDataKinds() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class StructuredName implements DataColumnsWithJoins
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/name";
        public static final String DISPLAY_NAME = "data1";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String FAMILY_NAME = "data3";
        public static final String FULL_NAME_STYLE = "data10";
        public static final String GIVEN_NAME = "data2";
        public static final String MIDDLE_NAME = "data5";
        public static final String PHONETIC_FAMILY_NAME = "data9";
        public static final String PHONETIC_GIVEN_NAME = "data7";
        public static final String PHONETIC_MIDDLE_NAME = "data8";
        public static final String PHONETIC_NAME_STYLE = "data11";
        public static final String PREFIX = "data4";
        public static final String SUFFIX = "data6";
        
        StructuredName() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Nickname implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/nickname";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String NAME = "data1";
        public static final int TYPE_DEFAULT = 1;
        public static final int TYPE_INITIALS = 5;
        public static final int TYPE_MAIDEN_NAME = 3;
        @Deprecated
        public static final int TYPE_MAINDEN_NAME = 3;
        public static final int TYPE_OTHER_NAME = 2;
        public static final int TYPE_SHORT_NAME = 4;
        
        Nickname() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Phone implements DataColumnsWithJoins, CommonColumns
    {
        public static final Uri CONTENT_FILTER_URI;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone_v2";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_v2";
        public static final Uri CONTENT_URI;
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String NORMALIZED_NUMBER = "data4";
        public static final String NUMBER = "data1";
        public static final String SEARCH_DISPLAY_NAME_KEY = "search_display_name";
        public static final String SEARCH_PHONE_NUMBER_KEY = "search_phone_number";
        public static final int TYPE_ASSISTANT = 19;
        public static final int TYPE_CALLBACK = 8;
        public static final int TYPE_CAR = 9;
        public static final int TYPE_COMPANY_MAIN = 10;
        public static final int TYPE_FAX_HOME = 5;
        public static final int TYPE_FAX_WORK = 4;
        public static final int TYPE_HOME = 1;
        public static final int TYPE_ISDN = 11;
        public static final int TYPE_MAIN = 12;
        public static final int TYPE_MMS = 20;
        public static final int TYPE_MOBILE = 2;
        public static final int TYPE_OTHER = 7;
        public static final int TYPE_OTHER_FAX = 13;
        public static final int TYPE_PAGER = 6;
        public static final int TYPE_RADIO = 14;
        public static final int TYPE_TELEX = 15;
        public static final int TYPE_TTY_TDD = 16;
        public static final int TYPE_WORK = 3;
        public static final int TYPE_WORK_MOBILE = 17;
        public static final int TYPE_WORK_PAGER = 18;
        
        Phone() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_FILTER_URI = null;
            CONTENT_URI = null;
            ENTERPRISE_CONTENT_FILTER_URI = null;
        }
    }
    
    public static final class Email implements DataColumnsWithJoins, CommonColumns
    {
        public static final String ADDRESS = "data1";
        public static final Uri CONTENT_FILTER_URI;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/email_v2";
        public static final Uri CONTENT_LOOKUP_URI;
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/email_v2";
        public static final Uri CONTENT_URI;
        public static final String DISPLAY_NAME = "data4";
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
        public static final Uri ENTERPRISE_CONTENT_LOOKUP_URI;
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final int TYPE_HOME = 1;
        public static final int TYPE_MOBILE = 4;
        public static final int TYPE_OTHER = 3;
        public static final int TYPE_WORK = 2;
        
        Email() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_FILTER_URI = null;
            CONTENT_LOOKUP_URI = null;
            CONTENT_URI = null;
            ENTERPRISE_CONTENT_FILTER_URI = null;
            ENTERPRISE_CONTENT_LOOKUP_URI = null;
        }
    }
    
    public static final class StructuredPostal implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CITY = "data7";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/postal-address_v2";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/postal-address_v2";
        public static final Uri CONTENT_URI;
        public static final String COUNTRY = "data10";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String FORMATTED_ADDRESS = "data1";
        public static final String NEIGHBORHOOD = "data6";
        public static final String POBOX = "data5";
        public static final String POSTCODE = "data9";
        public static final String REGION = "data8";
        public static final String STREET = "data4";
        public static final int TYPE_HOME = 1;
        public static final int TYPE_OTHER = 3;
        public static final int TYPE_WORK = 2;
        
        StructuredPostal() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_URI = null;
        }
    }
    
    public static final class Im implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im";
        public static final String CUSTOM_PROTOCOL = "data6";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String PROTOCOL = "data5";
        public static final int PROTOCOL_AIM = 0;
        public static final int PROTOCOL_CUSTOM = -1;
        public static final int PROTOCOL_GOOGLE_TALK = 5;
        public static final int PROTOCOL_ICQ = 6;
        public static final int PROTOCOL_JABBER = 7;
        public static final int PROTOCOL_MSN = 1;
        public static final int PROTOCOL_NETMEETING = 8;
        public static final int PROTOCOL_QQ = 4;
        public static final int PROTOCOL_SKYPE = 3;
        public static final int PROTOCOL_YAHOO = 2;
        public static final int TYPE_HOME = 1;
        public static final int TYPE_OTHER = 3;
        public static final int TYPE_WORK = 2;
        
        Im() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getProtocolLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getProtocolLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Organization implements DataColumnsWithJoins, CommonColumns
    {
        public static final String COMPANY = "data1";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/organization";
        public static final String DEPARTMENT = "data5";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String JOB_DESCRIPTION = "data6";
        public static final String OFFICE_LOCATION = "data9";
        public static final String PHONETIC_NAME = "data8";
        public static final String PHONETIC_NAME_STYLE = "data10";
        public static final String SYMBOL = "data7";
        public static final String TITLE = "data4";
        public static final int TYPE_OTHER = 2;
        public static final int TYPE_WORK = 1;
        
        Organization() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Relation implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/relation";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String NAME = "data1";
        public static final int TYPE_ASSISTANT = 1;
        public static final int TYPE_BROTHER = 2;
        public static final int TYPE_CHILD = 3;
        public static final int TYPE_DOMESTIC_PARTNER = 4;
        public static final int TYPE_FATHER = 5;
        public static final int TYPE_FRIEND = 6;
        public static final int TYPE_MANAGER = 7;
        public static final int TYPE_MOTHER = 8;
        public static final int TYPE_PARENT = 9;
        public static final int TYPE_PARTNER = 10;
        public static final int TYPE_REFERRED_BY = 11;
        public static final int TYPE_RELATIVE = 12;
        public static final int TYPE_SISTER = 13;
        public static final int TYPE_SPOUSE = 14;
        
        Relation() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Event implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_event";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String START_DATE = "data1";
        public static final int TYPE_ANNIVERSARY = 1;
        public static final int TYPE_BIRTHDAY = 3;
        public static final int TYPE_OTHER = 2;
        
        Event() {
            throw new RuntimeException("Stub!");
        }
        
        public static int getTypeResource(final Integer type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Photo implements DataColumnsWithJoins
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/photo";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String PHOTO = "data15";
        public static final String PHOTO_FILE_ID = "data14";
        
        Photo() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Note implements DataColumnsWithJoins
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/note";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String NOTE = "data1";
        
        Note() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class GroupMembership implements DataColumnsWithJoins
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group_membership";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String GROUP_ROW_ID = "data1";
        public static final String GROUP_SOURCE_ID = "group_sourceid";
        
        GroupMembership() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Website implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/website";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final int TYPE_BLOG = 2;
        public static final int TYPE_FTP = 6;
        public static final int TYPE_HOME = 4;
        public static final int TYPE_HOMEPAGE = 1;
        public static final int TYPE_OTHER = 7;
        public static final int TYPE_PROFILE = 3;
        public static final int TYPE_WORK = 5;
        public static final String URL = "data1";
        
        Website() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class SipAddress implements DataColumnsWithJoins, CommonColumns
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sip_address";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String SIP_ADDRESS = "data1";
        public static final int TYPE_HOME = 1;
        public static final int TYPE_OTHER = 3;
        public static final int TYPE_WORK = 2;
        
        SipAddress() {
            throw new RuntimeException("Stub!");
        }
        
        public static final int getTypeLabelResource(final int type) {
            throw new RuntimeException("Stub!");
        }
        
        public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Identity implements DataColumnsWithJoins
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/identity";
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String IDENTITY = "data1";
        public static final String NAMESPACE = "data2";
        
        Identity() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Callable implements DataColumnsWithJoins, CommonColumns
    {
        public static final Uri CONTENT_FILTER_URI;
        public static final Uri CONTENT_URI;
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        
        public Callable() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_FILTER_URI = null;
            CONTENT_URI = null;
            ENTERPRISE_CONTENT_FILTER_URI = null;
        }
    }
    
    public static final class Contactables implements DataColumnsWithJoins, CommonColumns
    {
        public static final Uri CONTENT_FILTER_URI;
        public static final Uri CONTENT_URI;
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
        public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";
        
        public Contactables() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CONTENT_FILTER_URI = null;
            CONTENT_URI = null;
        }
    }
    
    protected interface CommonColumns extends BaseTypes
    {
        public static final String DATA = "data1";
        public static final String LABEL = "data3";
        public static final String TYPE = "data2";
    }
    
    public interface BaseTypes
    {
        public static final int TYPE_CUSTOM = 0;
    }
}
