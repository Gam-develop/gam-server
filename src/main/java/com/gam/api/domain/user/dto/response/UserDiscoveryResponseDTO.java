package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;

import com.gam.api.domain.work.entity.Work;
import lombok.Builder;

@Builder
public record UserDiscoveryResponseDTO(
		UserInfoVO UserInfo,
		WorkInfoVO WorkInfo
) {
	public static UserDiscoveryResponseDTO of(User user,boolean designerScrap, Work work){
		return UserDiscoveryResponseDTO.builder()
				.UserInfo(UserInfoVO.of(user,designerScrap))
				.WorkInfo(WorkInfoVO.of(work))
				.build();
	}
}

@Builder
record UserInfoVO(
		Long userId,
		int[] userTag,
		String userName,
		int viewCount,
		boolean designerScrap
){
	public static UserInfoVO of(User user, boolean designerScrap){
		return UserInfoVO.builder()
				.userId(user.getId())
				.userTag(user.getTags() == null ? new int[0] : user.getTags())
				.userName(user.getUserName())
				.viewCount(user.getViewCount())
				.designerScrap(designerScrap)
				.build();
	}
}

@Builder
record WorkInfoVO(
		Long workId,
		String workTitle,
		String workDetail,
		String photoUrl
){
	public static WorkInfoVO of (Work work){
		return WorkInfoVO.builder()
				.workId(work.getId())
				.workTitle(work.getTitle())
				.workDetail(work.getDetail())
				.photoUrl(work.getPhotoUrl())
				.build();
	}
}


