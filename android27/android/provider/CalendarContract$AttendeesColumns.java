package android.provider;

protected interface AttendeesColumns
{
    public static final String ATTENDEE_EMAIL = "attendeeEmail";
    public static final String ATTENDEE_IDENTITY = "attendeeIdentity";
    public static final String ATTENDEE_ID_NAMESPACE = "attendeeIdNamespace";
    public static final String ATTENDEE_NAME = "attendeeName";
    public static final String ATTENDEE_RELATIONSHIP = "attendeeRelationship";
    public static final String ATTENDEE_STATUS = "attendeeStatus";
    public static final int ATTENDEE_STATUS_ACCEPTED = 1;
    public static final int ATTENDEE_STATUS_DECLINED = 2;
    public static final int ATTENDEE_STATUS_INVITED = 3;
    public static final int ATTENDEE_STATUS_NONE = 0;
    public static final int ATTENDEE_STATUS_TENTATIVE = 4;
    public static final String ATTENDEE_TYPE = "attendeeType";
    public static final String EVENT_ID = "event_id";
    public static final int RELATIONSHIP_ATTENDEE = 1;
    public static final int RELATIONSHIP_NONE = 0;
    public static final int RELATIONSHIP_ORGANIZER = 2;
    public static final int RELATIONSHIP_PERFORMER = 3;
    public static final int RELATIONSHIP_SPEAKER = 4;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_OPTIONAL = 2;
    public static final int TYPE_REQUIRED = 1;
    public static final int TYPE_RESOURCE = 3;
}
