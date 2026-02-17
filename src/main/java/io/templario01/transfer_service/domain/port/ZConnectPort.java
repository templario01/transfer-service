package io.templario01.transfer_service.domain.port;

import com.io.templario01.transfer_service.adapter.contract.ZConnectResponse;
import io.templario01.transfer_service.domain.entity.Transfer;
import reactor.core.publisher.Mono;

public interface ZConnectPort {
    Mono<ZConnectResponse> notifyMainframe(Transfer transfer);

}
