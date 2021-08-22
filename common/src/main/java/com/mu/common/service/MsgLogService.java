package com.mu.common.service;

import com.mu.common.entity.MsgLog;

import java.util.List;

public interface MsgLogService {

    /**
     * 插入消息
     *
     * @param msgLog msgLog
     */
    void insertMsgLog(MsgLog msgLog);

    /**
     * 更新状态
     *
     * @param msgLog msgLog
     */
    void updateStatus(String msgLog, Integer status);

    /**
     * 查询消息(是否消息过)
     *
     * @param msgId msgId
     * @return
     */
    MsgLog getMsgId(String msgId);

    /**
     * 获取消息信息列表(第一投递失败的)
     *
     * @return
     */
    List<MsgLog> getTimeoutMsg();

    /**
     * 更新重新投递次数
     *
     * @param msgId msgId
     * @param nextTryTime nextTryTime
     */
    void updateTryCount(String msgId, String nextTryTime);
}
