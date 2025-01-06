package com.yss.baseservice.domain.system;

import com.yss.baseservice.application.system.dto.LoginLogDto;
import com.yss.baseservice.domain.system.entity.LoginLog;
import com.yss.baseservice.domain.system.mapper.LoginLogMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 登录日志领域层
 * @author lijianbin
 * @date 2021-09-18 11:46
 **/
@Service
public class LoginLogDomainService {
    private final LoginLogMapper loginLogMapper;
    public LoginLogDomainService(LoginLogMapper loginLogMapper) {
        this.loginLogMapper = loginLogMapper;
    }

    /**
     * 查询登录日志
     * @param username  用户名
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param rowBounds 分页
     * @return  登录日志
     */
    public List<LoginLogDto> selectLoginLog(String username, Date startDate, Date endDate, RowBounds rowBounds) {
        return loginLogMapper.selectLoginLog(username, startDate, endDate, rowBounds);
    }

    /**
     * 保存登录日志
     * @param realName         用户名
     * @param remark           备注
     * @param ipAddress        ip地址
     */
    public void saveLoginLog( String realName,String ipAddress,String remark,String address,String userId,Integer type) {
        /**
         * 参数信息：
         * int corePoolSize     核心线程大小
         * int maximumPoolSize  线程池最大容量大小
         * long keepAliveTime   线程空闲时，线程存活的时间
         * TimeUnit unit        时间单位
         * BlockingQueue<Runnable> workQueue  任务队列。一个阻塞队列
         */
        // ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,4, 10000, TimeUnit.SECONDS,
        //         new LinkedBlockingDeque<>(3));
        // threadPoolExecutor.execute(() -> {
            LoginLog loginLog = LoginLog.createLoginLog(realName, ipAddress, remark, address,userId,type);
            loginLog.insert();
        // });
    }
}
