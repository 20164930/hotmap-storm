package st;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mysql.jdbc.Connection;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;




public class PrintBolt extends BaseBasicBolt {

    public static final Log log = LogFactory.getLog(PrintBolt.class);

    public static final long serialVersionUID = 1L;

    public static int count = 0;
    public static Connection con;
    static {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.88.128:3306/storm", "root", "www.6767dnf.com");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        //获取上一个组件所声明的Field
        //String print = input.getStringByField("print");
        count++;
        String print = input.getString(0);
//      log.info("【print】： " + print);
        String[] arr = print.split("\\t");
        System.out.println("Name of input word is : " + print);
        //保存到mysql
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);

            String sql = "insert into hotgra (time,latitude,longitude) values(?,?,?)";
            PreparedStatement pst =  con.prepareStatement(sql);
            System.out.println(pst);
            //调用pst对象set方法,设置问号占位符上的参数
            Date date = new Date();
            pst.setLong(1, date.getTime());
            pst.setDouble(2, Double.parseDouble(arr[1].split(",")[1]));
            pst.setDouble(3, Double.parseDouble(arr[1].split(",")[0]));
            pst.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //进行传递给下一个bolt
        //collector.emit(new Values(print));
        System.out.println(count);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //declarer.declare(new Fields("write"));
    }

}
