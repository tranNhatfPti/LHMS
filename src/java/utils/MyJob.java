/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author admin
 */
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import model.Contract;
import dal.ContractDAO;
import java.util.List;
import dal.AccountInRoomDAO;
import model.AccountInRoom;
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Lấy dữ liệu từ JobDataMap của JobDetail
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
      
        // Thực hiện các hành động cụ thể khi công việc được kích hoạt
        checkDateContract();
        // Hoặc thực hiện các tác vụ khác như gửi email, lưu dữ liệu, vv.
    }

    private void checkDateContract() {
        ContractDAO contractDAO = new ContractDAO();
        AccountInRoomDAO accountInRoomDAO = new AccountInRoomDAO();
        List<Contract> listContractExpired = contractDAO.getContractExpired();
        
        if (listContractExpired != null) {
            System.out.println("Cheking...");
            for (Contract contract : listContractExpired) {            
                contract.setStatusDelete(0);
                contractDAO.updateContract(contract);
                AccountInRoom accountInRoom = accountInRoomDAO.getAccountRoomsById(contract.getRoom().getRoomId(),contract.getTenantId().getAccountID());
                accountInRoomDAO.deleteAccountInRoom(accountInRoom);
            }
        }else{
            System.out.println("Check susscess!");
        }

    }
    
}
