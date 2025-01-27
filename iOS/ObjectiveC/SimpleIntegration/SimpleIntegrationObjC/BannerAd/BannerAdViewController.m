//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

#import "BannerAdViewController.h"
#import <AppNexusSDK/AppNexusSDK.h>



@interface BannerAdViewController () <ANBannerAdViewDelegate>

@property (nonatomic, readwrite, strong) ANBannerAdView *banner;

@property (nonatomic, readwrite, strong) NSDate *processStart;
@property (nonatomic, readwrite, strong) NSDate *processEnd;
@property (nonatomic, readwrite, strong) NSDate *previousDifference;
@end

@implementation BannerAdViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"Banner Ad";

    int adWidth  = 300;
    int adHeight = 250;
    NSString *adID = @"17058950";
    
    int adWidth1  = 320;
    int adHeight1 = 50;
    
    // We want to center our ad on the screen.
    CGRect screenRect = [[UIScreen mainScreen] bounds];
    CGFloat originX = (screenRect.size.width / 2) - (adWidth / 2);
    CGFloat originY = (screenRect.size.height / 2) - (adHeight / 2);
    
    // Needed for when we create our ad view.
    CGRect rect = CGRectMake(originX, originY, adWidth, adHeight);
    CGSize size = CGSizeMake(adWidth, adHeight);
    
    // Make a banner ad view.
    self.banner = [ANBannerAdView adViewWithFrame:rect placementId:adID adSize:size];
    
    // Needed for when we create our ad view.
    CGRect rect1 = CGRectMake(originX, originY, adWidth1, adHeight1);
    CGSize size1 = CGSizeMake(adWidth1, adHeight1);
    
    //self.banner = [[ANBannerAdView alloc] initWithFrame:rect1 memberId:memberID inventoryCode:inventoryCode adSize:size1];
    self.banner.rootViewController = self;
    self.banner.delegate = self;
    self.banner.enableLazyLoad = YES;
    [self.view addSubview:self.banner];
    
    // Since this example is for testing, we'll turn on PSAs and verbose logging.
    self.banner.shouldServePublicServiceAnnouncements = NO;
    self.banner.autoRefreshInterval = 10;
    
    // Load an ad.
    self.processStart = [NSDate date];
    [self.banner loadAd];
}

- (void)adDidReceiveAd:(id)ad {
    NSLog(@"Ad did receive ad");
    self.processEnd = [NSDate date];
    NSTimeInterval executionTime = [self.processEnd timeIntervalSinceDate:self.processStart];
    NSLog(@"Updated Ad rendered at: %f", executionTime*1000);
}

-(void) lazyAdDidReceiveAd:(id)ad {
    [self.banner loadLazyAd];
}

-(void)ad:(id)ad requestFailedWithError:(NSError *)error{
    NSLog(@"Ad request Failed With Error");
    self.processEnd = [NSDate date];
    NSTimeInterval executionTime = [self.processEnd timeIntervalSinceDate:self.processStart];
    NSLog(@"Updated Ad delivery failed at: %f", executionTime*1000);
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
