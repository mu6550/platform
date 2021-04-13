package com.mu.common.mapper;

import com.mu.common.entity.MsgLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mujiangkui
 * @Description dao
 * @Date 2021/01/24 23:10:10
 */
@Mapper
public interface MsgLogMapper {
    /**
     * 发送的消息入库保存
     *
     * @param msgLog msgLog
     */
    void insertMsgLog(MsgLog msgLog);

    /**
     * 更新邮件发送的状态
     *
     * @param msgId  msgId
     * @param status 状态
     */
    void updateStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    /**
     * 获取消息列表
     *
     * @return java.util.List<com.mu.common.entity.MsgLog>
     */
    List<MsgLog> getMsgList();

    /**
     * * 查询消息(是否消息过)
     *
     * @param msgId msgId
     * @return com.mu.common.entity.MsgLog
     */
    MsgLog getMsgId(String msgId);


    /**
     * 获取消息超时
     *
     * @return java.util.List<com.mu.common.entity.MsgLog>
     */
    List<MsgLog> getTimeoutMsg();

    /**
     * 更新下一次投递时间
     *
     * @param msgId       msgId
     * @param nextTryTime nextTryTime
     */
    void updateTryCount(String msgId, String nextTryTime);
}
