package com.intersem.sdib.ui.services.helpers;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition,int toPosition);
    void onItemDismiss(int position);

}
