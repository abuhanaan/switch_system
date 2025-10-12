package com.example.demo.models.dto.response;

import com.example.demo.models.entities.Account;
import com.example.demo.models.entities.Payin;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecentTransactions {

  private List<BillsPaymentResponse> bills;
  private List<DashboardPayoutDto> transfers;
//  private List<Payin> payins;
}
