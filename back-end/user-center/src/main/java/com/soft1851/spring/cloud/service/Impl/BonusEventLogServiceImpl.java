package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.spring.cloud.domain.entity.BonusEventLog;
import com.soft1851.spring.cloud.mapper.BonusEventLogMapper;
import com.soft1851.spring.cloud.service.BonusEventLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/12
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusEventLogServiceImpl implements BonusEventLogService {
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public int insertBonusEventLog(int userId, int bonus) {
        return bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(bonus)
                .event("CONTRIBUTE")
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .description("下载分享扣分")
                .build());
    }
}
