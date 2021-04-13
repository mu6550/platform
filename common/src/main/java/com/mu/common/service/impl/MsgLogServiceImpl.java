package com.mu.common.service.impl;

import com.mu.common.entity.MsgLog;
import com.mu.common.mapper.MsgLogMapper;
import com.mu.common.service.MsgLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgLogServiceImpl implements MsgLogService {

    @Autowired
    private MsgLogMapper msgLogMapper;


    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void insertMsgLog(MsgLog msgLog) {
        msgLogMapper.insertMsgLog(msgLog);
    }

    /**
     * 更新消息状态
     *
     * @param msgId
     * @param status
     */
    @Override
    public void updateStatus(String msgId, Integer status) {
        msgLogMapper.updateStatus(msgId, status);
    }

    /**
     * 根据msgId查询消息
     *
     * @param msgId
     * @return
     */
    @Override
    @Cacheable(value = "users", key = "msgId")
    public MsgLog getMsgId(String msgId) {
        return msgLogMapper.getMsgId(msgId);
    }

    @Override
    public List<MsgLog> getTimeoutMsg() {
        return msgLogMapper.getTimeoutMsg();
    }

    @Override
    public void updateTryCount(String msgId, String nextTryTime) {
        msgLogMapper.updateTryCount(msgId, nextTryTime);
    }
}
