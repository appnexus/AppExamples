//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

#import "ANNativeAdView.h"


@implementation ANNativeAdView

@synthesize nativeResponse = _nativeResponse;

-(void) setNativeResponse:(ANNativeAdResponse *)nativeAdResponse {
    _nativeResponse = nativeAdResponse;
    self.titleLabel.text = nativeAdResponse.title;
    self.bodyLabel.text = nativeAdResponse.body;
    self.iconImageView.image = nativeAdResponse.iconImage;
    self.mainImageView.image = nativeAdResponse.mainImage;
    self.sponsoredLabel.text = nativeAdResponse.sponsoredBy;
    [self.callToActionButton setTitle:nativeAdResponse.callToAction forState:UIControlStateNormal];
}

@end
