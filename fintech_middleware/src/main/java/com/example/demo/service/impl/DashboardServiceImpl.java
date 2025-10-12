package com.example.demo.service.impl;

import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.BillsPaymentResponse;
import com.example.demo.models.dto.response.DashboardDto;
import com.example.demo.models.dto.response.DashboardPayoutDto;
import com.example.demo.models.dto.response.RecentTransactions;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.Account;
import com.example.demo.models.entities.BillsPayment;
import com.example.demo.models.entities.Payin;
import com.example.demo.models.entities.Payout;
import com.example.demo.models.entities.User;
import com.example.demo.repository.BillsRepository;
import com.example.demo.repository.PayinRepository;
import com.example.demo.repository.PayoutRepository;
import com.example.demo.service.DashboardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

  private final PayoutRepository payoutRepository;
//  private final PayinRepository payinRepository;
  private final BillsRepository billsRepository;

  @Override
  public ApiResponse getDashboard(User user) {
    Account account = user.getCustomer().getAccount();
    DashboardDto dto = DashboardDto.buildFromAccount(account);
    dto.setRecentTransactions(mapRecentTransations(account));
    return SuccessResponse.buildSuccess("Dashboard retrieved successfully!!", dto);
  }

  private RecentTransactions mapRecentTransations(Account account) {
//    List<Payin> recentPayin = payinRepository.findTop5ByAccountOrderByCreatedAtDesc(account);
    List<Payout> recentPayout = payoutRepository.findTop5ByAccountOrderByCreatedAtDesc(account);
    List<BillsPayment> recentBills = billsRepository.findTop5ByAccountOrderByCreatedAtDesc(account);
    return RecentTransactions.builder()
        .bills(BillsPaymentResponse.fromEntities(recentBills))
        .transfers(DashboardPayoutDto.fromEntities(recentPayout))
        .build();
  }
}
