package com.dtp.core.notify;

import com.dtp.common.dto.DtpMainProp;
import com.dtp.common.em.NotifyTypeEnum;

import java.util.List;

/**
 * Notifier related
 *
 * @author: yanhom
 * @since 1.0.0
 **/
public interface Notifier {

    /**
     * Get send platform.
     *
     * @return platform
     */
    String platform();

    /**
     * Send notify message.
     *
     * @param oldProp old properties
     * @param diffs the changed keys
     */
    void sendChangeMsg(DtpMainProp oldProp, List<String> diffs);

    /**
     * Send alarm message.
     * @param typeEnum notify type
     */
    void sendAlarmMsg(NotifyTypeEnum typeEnum);
}
