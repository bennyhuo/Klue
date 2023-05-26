#import <Foundation/Foundation.h>

typedef struct objc_class *Class;
extern void RCTRegisterModule(Class);

typedef struct RCTMethodInfo {
    const char *const jsName;
    const char *const objcName;
    const BOOL isSync;
} RCTMethodInfo;

@protocol RCTBridgeModule <NSObject>

+ (NSString *)moduleName;

@end
