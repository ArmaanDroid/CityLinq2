package fragments.passes;

import models.PurchasePass;

/**
 * Created by Armaan on 11-12-2017.
 */

class PassBoughtEvent {
    PurchasePass newPass;

    public PassBoughtEvent(PurchasePass pass) {
        newPass = pass;
    }
}
