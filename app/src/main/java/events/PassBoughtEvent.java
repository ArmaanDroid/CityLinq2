package events;

import models.PurchasePass;

/**
 * Created by Armaan on 11-12-2017.
 */

 public class PassBoughtEvent {
    public PurchasePass newPass;

    public PassBoughtEvent(PurchasePass pass) {
        newPass = pass;
    }
}
