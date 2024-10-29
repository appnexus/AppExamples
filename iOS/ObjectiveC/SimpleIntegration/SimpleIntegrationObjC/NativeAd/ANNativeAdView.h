//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

#import <UIKit/UIKit.h>
#import <AppNexusSDK/AppNexusSDK.h>

@interface ANNativeAdView : UIView
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UILabel *bodyLabel;
@property (weak, nonatomic) IBOutlet UIImageView *iconImageView;
@property (weak, nonatomic) IBOutlet UIImageView *mainImageView;
@property (weak, nonatomic) IBOutlet UIButton *callToActionButton;
@property (weak, nonatomic) IBOutlet UILabel *sponsoredLabel;

@property (nonatomic, strong) ANNativeAdResponse *nativeResponse;

@end
