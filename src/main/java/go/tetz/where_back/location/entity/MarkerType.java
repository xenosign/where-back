package go.tetz.where_back.location.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MarkerType {
    START_POINT("시작 지점"),
    ITEM_BOX("아이템 보급 상자"),
    HIDEOUT("은신처"),
    PRISON("감옥"),
    CHECKPOINT("체크포인트");

    private final String description;
}