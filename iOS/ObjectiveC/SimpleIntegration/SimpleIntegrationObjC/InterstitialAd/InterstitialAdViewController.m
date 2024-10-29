//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

#import "InterstitialAdViewController.h"
#import <AppNexusSDK/AppNexusSDK.h>

@interface InterstitialAdViewController () <ANInterstitialAdDelegate>{
    UIActivityIndicatorView *indicator;
}

@property (strong, nonatomic) ANInterstitialAd *interstitialAd;

@end

@implementation InterstitialAdViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"Interstitial Ad";
    self.interstitialAd = [[ANInterstitialAd alloc] initWithPlacementId:@"17058950"];
    self.interstitialAd.delegate = self;
    self.interstitialAd.clickThroughAction = ANClickThroughActionReturnURL;
    [self.interstitialAd loadAd];
}

#pragma mark - ANInterstitialAdDelegate

- (void)adDidReceiveAd:(id)ad {
    NSLog(@"adDidReceiveAd");
    [self.interstitialAd displayAdFromViewController:self];
}

-(void)ad:(id)ad requestFailedWithError:(NSError *)error{
    NSLog(@"Ad request Failed With Error");
}
@end
