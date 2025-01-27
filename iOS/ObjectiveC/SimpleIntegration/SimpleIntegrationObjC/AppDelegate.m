//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

#import "AppDelegate.h"
#import <AppNexusSDK/AppNexusSDK.h>

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{
    
    [[XandrAd sharedInstance] initWithMemberID:1234 preCacheRequestObjects:YES completionHandler:^(BOOL success){
        if(success){
          NSLog(@"Completion is called with status success ");
        }else{
          NSLog(@"Completion is called with status failed ");
        }
    }];
    return YES;
}

@end
