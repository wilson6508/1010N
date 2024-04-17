import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class ApplicationFormExceptionService {

    private static final Lock lock = new ReentrantLock();

    @Transactional
    public SmsResponse createForm(ApplicationFormExceptionRequest applicationFormExceptionRequest) throws InterruptedException {
        // 先存表單
        ApplicationFormExceptionEntity entity = new ApplicationFormExceptionEntity();
        BeanUtils.copyProperties(applicationFormExceptionRequest, entity);
        OpvAuthUtils.setFormFiller(entity);
        entity.setPromiseEffectiveDate(OpvAuthUtils.convertStringToTimestamp(applicationFormExceptionRequest.getPromiseEffectiveDate()));
        entity.setPromiseExpiryDate(OpvAuthUtils.convertStringToTimestamp(applicationFormExceptionRequest.getPromiseExpiryDate()));
        entity.setRealEffectiveDate(OpvAuthUtils.convertStringToTimestamp(applicationFormExceptionRequest.getRealEffectiveDate()));
        entity.setRealExpiryDate(OpvAuthUtils.convertStringToTimestamp(applicationFormExceptionRequest.getRealExpiryDate()));
        // 生成單號要上鎖
        String code;
        boolean getLock = lock.tryLock();
        while (!getLock) {
            Thread.sleep(1000);
            getLock = lock.tryLock();
        }
        try {
            ApplicationFormExceptionEntity maxIdEntity = applicationFromExceptionDao.findFirstByOrderByIdDesc();
            String formCode = OpvAuthUtils.getFormCode(FormConstants.CODE_TYPE_EXCEPTION, maxIdEntity);
            entity.setCode(formCode);
            entity.setStatus(FormConstants.STATUS_WAIT);
            ApplicationFormExceptionEntity done = applicationFromExceptionDao.save(entity);
            code = done.getCode();
        } finally {
            lock.unlock();
        }
        // 再存通知人和審核人
        List<Integer> noticeAccountIds = applicationFormExceptionRequest.getNoticeAccountIds();
        List<Integer> signAccountIds = applicationFormExceptionRequest.getSignAccountIds();
        applicationFormService.saveNoticeAndSign(code, noticeAccountIds, signAccountIds);
        return SmsResponse.ok(code);
    }

}
