package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;

import javax.sound.sampled.Port;

@Slf4j
public class KattleUtil {
//    @Value("${NAME}")
//    private static String name;
//    @Value("${PASS}")
//    private static String pass;
//    private static String name="PDINT";
//    private static String type="MS SQL Server";
//    private static String access="Native(JDBC)";
//    private static String host="10.3.24.84";
//    private static String db="PDINT";
//    private static String part="1433";
//    private static String user="sa";
//    private static String pass="password01!";

    private static String name = "ljm";
    private static String type = "MySQL";
    private static String access = "Native(JDBC)";
    private static String host = "192.168.100.32";
    private static String db = "house";
    private static String part = "3306";
    private static String user = "remote";
    private static String pass = "Shuqi@12345";

    /**
     * @author ljm
     * 配置资源库环境并接连接的资源库
     **/
    public static KettleDatabaseRepository RepositoryCon() throws KettleException {
        //初始化
        KettleEnvironment.init();
        //数据库连接元对象
        DatabaseMeta dataMeta = new DatabaseMeta(name, type, access, host, db, part, user, pass);
        //数据库形式的资源库元对象
        KettleDatabaseRepositoryMeta repInfo = new KettleDatabaseRepositoryMeta();
        repInfo.setConnection(dataMeta);

        //数据库形式的资源库对象
        KettleDatabaseRepository rep = new KettleDatabaseRepository();
        //用资源库元对象初始化资源库对象
        rep.init(repInfo);
        //连接到资源库
        rep.connect("admin", "admin");//默认的连接资源库的用户名和密码
        if (rep.isConnected()) {
            log.info("连接成功");
            return rep;
        } else {
            log.info("连接失败");
            return null;
        }

    }

    /**
     * 根据指定的资源库对象及job名称运行指定的job
     *
     * @param rep     资源库对象
     * @param jobName job名称
     * @autor ljm
     */
    public static boolean runJob(KettleDatabaseRepository rep, String jobName, String jobPath) throws Exception {

        try {
            RepositoryDirectoryInterface dir = rep.findDirectory("/"+jobPath);//根据指定的字符串路径 找到目录
            log.info("【--------------路径名称：{}，jobName:{},jobPath:{}】",dir,jobName,jobPath);
            //加载指定的job
            JobMeta jobMeta = rep.loadJob(rep.getJobId(jobName, dir), null);
            Job job = new Job(rep, jobMeta);
            job.setLogLevel(LogLevel.ERROR);
            //启动执行指定的job
            job.run();
            Thread.sleep(60000);
            job.waitUntilFinished();//等待job执行完；
            job.setFinished(true);
            System.out.println(job.getResult());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【job运行异常，method:runJob, args:jobName={}】", jobName);
            throw new Exception(e);
        }
    }

    /**
     * @param rep       资源库对象
     * @param transName 要调用的trans名称
     *                  调用资源库中的trans
     * @author ljm
     */
    public static void runTrans(KettleDatabaseRepository rep, String transName) {

        try {
            RepositoryDirectoryInterface dir = rep.findDirectory("/转换");//根据指定的字符串路径 找到目录
            TransMeta tmeta = rep.loadTransformation(rep.getTransformationID(transName, dir), null);
            //设置参数，有则设置没有则不需要
            Trans trans = new Trans(tmeta);
            trans.execute(null);//执行trans
            trans.waitUntilFinished();
            if (trans.getErrors() > 0) {
                log.error("有异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【trans运行异常，method:runTrans, args:transName={}】", transName);

        }
    }

    public static void main(String[] arg) throws KettleException {
        Object obj = RepositoryCon();
        if (obj != null) {//资源库连接成功
//            runJob((KettleDatabaseRepository) obj, "test2", "");//调用指定的job
//            runTrans((KettleDatabaseRepository) obj, "java_trans_test");//调用指定的tansformation
        }

    }
}
