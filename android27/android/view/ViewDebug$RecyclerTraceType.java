package android.view;

@Deprecated
public enum RecyclerTraceType
{
    BIND_VIEW, 
    MOVE_FROM_ACTIVE_TO_SCRAP_HEAP, 
    MOVE_TO_SCRAP_HEAP, 
    NEW_VIEW, 
    RECYCLE_FROM_ACTIVE_HEAP, 
    RECYCLE_FROM_SCRAP_HEAP;
}
