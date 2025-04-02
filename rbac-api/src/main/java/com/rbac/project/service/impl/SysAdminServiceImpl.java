package com.rbac.project.service.impl;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.rbac.common.constant.RedisConstant;
import com.rbac.common.exception.BusinessException;
import com.rbac.common.result.ResultCode;
import com.rbac.common.util.AddressUtils;
import com.rbac.common.util.IpUtils;
import com.rbac.common.util.ServletUtils;
import com.rbac.project.entity.SysAdmin;
import com.rbac.project.entity.SysAdminRole;
import com.rbac.project.entity.SysLoginInfo;
import com.rbac.project.entity.dto.*;
import com.rbac.project.entity.vo.*;
import com.rbac.project.mapper.SysAdminMapper;
import com.rbac.project.mapper.SysAdminRoleMapper;
import com.rbac.project.mapper.SysLoginInfoMapper;
import com.rbac.project.service.SysAdminService;
import com.rbac.project.service.SysLoginInfoService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户 服务层接口实现类
 */
@Slf4j
@Service
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements SysAdminService {

    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private SysAdminRoleMapper sysAdminRoleMapper;
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Value("${rbac.captchaType}")
    private String captchaType;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SysLoginInfoMapper sysLoginInfoMapper;

    @Override
    public Page<QuerySysAdminVo> querySysAdminPageList(BaseQueryDto baseQueryDto, QuerySysAdminDto querySysAdminDto) {
        return sysAdminMapper.querySysAdminPageList(new Page(baseQueryDto.getPageNum(), baseQueryDto.getPageSize()), querySysAdminDto);
    }

    @Override
    public Boolean addSysAdmin(AddSysAdminDto addSysAdminDto) {
        SysAdmin sysAdminByUsername = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("username", addSysAdminDto.getUsername()));
        if (!ObjectUtils.isEmpty(sysAdminByUsername)) {
            throw new BusinessException(ResultCode.USER_NAME_IS_EXIST.getCode(), ResultCode.USER_NAME_IS_EXIST.getMessage());
        }
        SysAdmin sysAdminByNickname = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("nickname", addSysAdminDto.getNickname()));
        if (!ObjectUtils.isEmpty(sysAdminByNickname)) {
            throw new BusinessException(ResultCode.NICKNAME_IS_EXIST.getCode(), ResultCode.NICKNAME_IS_EXIST.getMessage());
        }
        SysAdmin sysAdminByPhone = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("phone", addSysAdminDto.getPhone()));
        if (!ObjectUtils.isEmpty(sysAdminByPhone)) {
            throw new BusinessException(ResultCode.PHONE_IS_EXIST.getCode(), ResultCode.PHONE_IS_EXIST.getMessage());
        }
        SysAdmin sysAdminByEmail = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("email", addSysAdminDto.getEmail()));
        if (!ObjectUtils.isEmpty(sysAdminByEmail)) {
            throw new BusinessException(ResultCode.EMAIL_IS_EXIST.getCode(), ResultCode.EMAIL_IS_EXIST.getMessage());
        }
        SysAdmin sysAdmin = new SysAdmin()
                .setPostId(addSysAdminDto.getPostId())
                .setDeptId(addSysAdminDto.getDeptId())
                .setUsername(addSysAdminDto.getUsername())
                .setNickname(addSysAdminDto.getNickname())
                .setSex(addSysAdminDto.getSex())
                .setPassword(SaSecureUtil.aesEncrypt(addSysAdminDto.getPassword(), addSysAdminDto.getUsername()))
                .setEmail(addSysAdminDto.getEmail())
                .setPhone(addSysAdminDto.getPhone())
                .setNote(addSysAdminDto.getNote())
                .setStatus(addSysAdminDto.getStatus())
                .setCreateTime(new Date());
        int count = sysAdminMapper.insert(sysAdmin);
        if (count > 0) {
            SysAdmin admin = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("username", addSysAdminDto.getUsername()));
            SysAdminRole sysAdminRole = new SysAdminRole().setAdminId(admin.getId()).setRoleId(addSysAdminDto.getRoleId());
            sysAdminRoleMapper.insert(sysAdminRole);
            return true;
        }
        return false;
    }

    @Override
    public SysAdminVo querySysAdminVoById(Integer id) {
        return sysAdminMapper.querySysAdminVoById(id);
    }

    @Override
    public Boolean updateSysAdmin(EditSysAdminDto editSysAdminDto) {
        SysAdmin sysAdminByUsername = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("username", editSysAdminDto.getUsername()));
        if (!ObjectUtils.isEmpty(sysAdminByUsername) && sysAdminByUsername.getId() != editSysAdminDto.getId()) {
            throw new BusinessException(ResultCode.USER_NAME_IS_EXIST.getCode(), ResultCode.USER_NAME_IS_EXIST.getMessage());
        }
        SysAdmin sysAdminByNickname = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("nickname", editSysAdminDto.getNickname()));
        if (!ObjectUtils.isEmpty(sysAdminByNickname) && sysAdminByNickname.getId() != editSysAdminDto.getId()) {
            throw new BusinessException(ResultCode.NICKNAME_IS_EXIST.getCode(), ResultCode.NICKNAME_IS_EXIST.getMessage());
        }
        SysAdmin sysAdminByPhone = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("phone", editSysAdminDto.getPhone()));
        if (!ObjectUtils.isEmpty(sysAdminByPhone) && sysAdminByPhone.getId() != editSysAdminDto.getId()) {
            throw new BusinessException(ResultCode.PHONE_IS_EXIST.getCode(), ResultCode.PHONE_IS_EXIST.getMessage());
        }
        SysAdmin sysAdminByEmail = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("email", editSysAdminDto.getEmail()));
        if (!ObjectUtils.isEmpty(sysAdminByEmail) && sysAdminByEmail.getId() != editSysAdminDto.getId()) {
            throw new BusinessException(ResultCode.EMAIL_IS_EXIST.getCode(), ResultCode.EMAIL_IS_EXIST.getMessage());
        }
        SysAdmin existSysAdmin = sysAdminMapper.selectById(editSysAdminDto.getId());
        if (ObjectUtils.isEmpty(existSysAdmin)) {
            throw new BusinessException(ResultCode.QUERY_ADMIN_IS_NULL.getCode(), ResultCode.QUERY_ADMIN_IS_NULL.getMessage());
        }
        BeanUtils.copyProperties(editSysAdminDto, existSysAdmin);
        int count = sysAdminMapper.updateById(existSysAdmin);
        if (count > 0) {
            sysAdminRoleMapper.delete(new QueryWrapper<SysAdminRole>().eq("admin_id", editSysAdminDto.getId()));
            SysAdminRole sysAdminRole = new SysAdminRole().setAdminId(editSysAdminDto.getId()).setRoleId(editSysAdminDto.getRoleId());
            sysAdminRoleMapper.insert(sysAdminRole);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSysAdmin(Integer id) {
        int count = sysAdminMapper.deleteById(id);
        if (count > 0) {
            sysAdminRoleMapper.delete(new QueryWrapper<SysAdminRole>().eq("admin_id", id));
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateSysAdminStatus(UpdateStatusDto updateStatusDto) {
        SysAdmin sysAdmin = sysAdminMapper.selectById(updateStatusDto.getId());
        sysAdmin.setStatus(updateStatusDto.getStatus());
        sysAdminMapper.updateById(sysAdmin);
        return true;
    }

    @Override
    public Boolean resetPassword(ResetPasswordDto resetPasswordDto) {
        SysAdmin sysAdmin = sysAdminMapper.selectById(resetPasswordDto.getId());
        sysAdmin.setPassword(SaSecureUtil.aesEncrypt(resetPasswordDto.getPassword(), sysAdmin.getUsername()));
        int count = sysAdminMapper.updateById(sysAdmin);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public SysAdmin updateCurrentSysAdmin(SysAdminEditDto sysAdminEditDto) {
        Integer currentAdminId = StpUtil.getLoginIdAsInt();
        SysAdmin sysAdmin = sysAdminMapper.selectById(currentAdminId);
        BeanUtils.copyProperties(sysAdminEditDto, sysAdmin);
        sysAdminMapper.updateById(sysAdmin);
        return sysAdmin;
    }

    @Override
    public Boolean updateCurrentAdminPassword(AdminResetPwdDto adminResetPwdDto) {
        Integer currentAdminId = StpUtil.getLoginIdAsInt();
        SysAdmin sysAdmin = sysAdminMapper.selectById(currentAdminId);
        if (!sysAdmin.getPassword().equals(SaSecureUtil.aesEncrypt(adminResetPwdDto.getOldPassword(), sysAdmin.getUsername()))) {
            throw new BusinessException(ResultCode.OLD_PASSWORD_IS_FALSE.getCode(), ResultCode.OLD_PASSWORD_IS_FALSE.getMessage());
        }
        if (!adminResetPwdDto.getNewPassword().equals(adminResetPwdDto.getResetPassword())) {
            throw new BusinessException(ResultCode.THE_NEW_PASSWORD_IS_NOT_THE_SAME_AS_THE_REPEATED_PASSWORD.getCode(),
                    ResultCode.THE_NEW_PASSWORD_IS_NOT_THE_SAME_AS_THE_REPEATED_PASSWORD.getMessage());
        }
        sysAdmin.setPassword(SaSecureUtil.aesEncrypt(adminResetPwdDto.getNewPassword(), sysAdmin.getUsername()));
        int count = sysAdminMapper.updateById(sysAdmin);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public CaptchaVo captcha(HttpServletResponse response) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String verifyKey = RedisConstant.CAPTCHA_CODE_KEY + uuid;
        String capStr = null, code = null;
        BufferedImage image = null;
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            log.info("数组验证码：{}", code);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            log.info("字符验证码：{}", code);
            image = captchaProducer.createImage(capStr);
        }
        redisTemplate.opsForValue().set(verifyKey, code, 5L, TimeUnit.MINUTES);
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", fastByteArrayOutputStream);
        } catch (IOException e) {
            log.error("验证码错误：{}", e.getMessage());
        }
        String str = "data:image/jpeg;base64,";
        return new CaptchaVo().setUuid(uuid).setImage(str + Base64.encode(fastByteArrayOutputStream.toByteArray()));
    }

    @Override
    public LoginSuccessVo login(SysAdminLoginDto sysAdminLoginDto) {
        String captchaRedis = (String) redisTemplate.opsForValue().get(RedisConstant.CAPTCHA_CODE_KEY + sysAdminLoginDto.getUuid());
        if (null == captchaRedis) {
            insetSysLoginInfo(sysAdminLoginDto.getUsername(), ResultCode.LOGIN_FAIL.getCode(), ResultCode.CAPTCHA_LOSE_EFFICACY.getMessage());
            throw new BusinessException(ResultCode.CAPTCHA_LOSE_EFFICACY.getCode(), ResultCode.CAPTCHA_LOSE_EFFICACY.getMessage());
        }
        if (!sysAdminLoginDto.getImage().equalsIgnoreCase(captchaRedis)) {
            insetSysLoginInfo(sysAdminLoginDto.getUsername(), ResultCode.LOGIN_FAIL.getCode(), ResultCode.CAPTCHA_IS_ERROR.getMessage());
            throw new BusinessException(ResultCode.CAPTCHA_IS_ERROR.getCode(), ResultCode.CAPTCHA_IS_ERROR.getMessage());
        }
        SysAdmin sysAdmin = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("username", sysAdminLoginDto.getUsername()));
        if (ObjectUtils.isEmpty(sysAdmin)) {
            throw new BusinessException(ResultCode.USER_NAME_IS_NOT_EXIST.getCode(), ResultCode.USER_NAME_IS_NOT_EXIST.getMessage());
        }
        if (!sysAdmin.getPassword().equals(SaSecureUtil.aesEncrypt(sysAdminLoginDto.getPassword(), sysAdmin.getUsername()))) {
            insetSysLoginInfo(sysAdminLoginDto.getUsername(), ResultCode.LOGIN_FAIL.getCode(), ResultCode.PASSWORD_IS_NOT_TRUE.getMessage());
            throw new BusinessException(ResultCode.PASSWORD_IS_NOT_TRUE.getCode(), ResultCode.PASSWORD_IS_NOT_TRUE.getMessage());
        }
        if (sysAdmin.getStatus() == ResultCode.DISABLE.getCode()) {
            insetSysLoginInfo(sysAdminLoginDto.getUsername(), ResultCode.LOGIN_FAIL.getCode(), ResultCode.USERNAME_IS_DISABLE.getMessage());
            throw new BusinessException(ResultCode.USERNAME_IS_DISABLE.getCode(), ResultCode.USERNAME_IS_DISABLE.getMessage());
        }
        sysAdmin.setPassword("******");
        StpUtil.login(sysAdmin.getId());
        String token = StpUtil.getTokenInfo().getTokenValue();
        insetSysLoginInfo(sysAdminLoginDto.getUsername(), ResultCode.LOGIN_SUCCESS.getCode(), ResultCode.LOGIN_SUCCESS.getMessage());
        List<String> valuesList = sysAdminMapper.queryValuesList(sysAdmin.getId());
        List<SysMenuOneVo> list = sysAdminMapper.treeList(sysAdmin.getId());
        for (SysMenuOneVo sysMenuOneVo : list) {
            List<SysMenuTwoVo> sysMenuTwoVoList = sysAdminMapper.menuList(sysAdmin.getId(), sysMenuOneVo.getId());
            sysMenuOneVo.setChildren(sysMenuTwoVoList);
        }
        return new LoginSuccessVo().setSysAdmin(sysAdmin).setToken(token).setValuesList(valuesList).setList(list);
    }

    @Override
    public Boolean logout() {
        StpUtil.logout();
        return true;
    }

    private void insetSysLoginInfo(String username, Integer loginStatus, String message) {
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        String addressByIp = AddressUtils.getRealAddressByIP(ip);
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String browser = userAgent.getBrowser().getName();
        String os = userAgent.getOperatingSystem().toString();
        SysLoginInfo sysLoginInfo = new SysLoginInfo()
                .setUsername(username)
                .setIpAddress(ip)
                .setLoginLocation(addressByIp)
                .setBrowser(browser)
                .setOs(os)
                .setLoginStatus(loginStatus)
                .setMessage(message)
                .setLoginTime(new Date());
        sysLoginInfoMapper.insert(sysLoginInfo);
    }

}
