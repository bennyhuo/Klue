#
# Be sure to run `pod lib lint SampleBridgeWrapper.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see https://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'SampleBridgeWrapper'
  s.version          = '0.1.0'
  s.summary          = 'A short description of SampleBridgeWrapper.'

# This description is used to generate tags and improve search results.
#   * Think: What does it do? Why did you write it? What is the focus?
#   * Try to keep it short, snappy and to the point.
#   * Write the description between the DESC delimiters below.
#   * Finally, don't worry about the indent, CocoaPods strips it!

  s.description      = <<-DESC
TODO: Add long description of the pod here.
                       DESC

  s.homepage         = 'https://github.com/bennyhuo/SampleBridgeWrapper'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'bennyhuo' => 'bennyhuo@kotliner.cn' }
  s.source           = { :git => 'https://github.com/bennyhuo/SampleBridgeWrapper.git', :tag => s.version.to_s }

  s.ios.deployment_target = '10.0'

  s.source_files = 'KlueModule.swift'

  s.dependency 'SampleBridge'
  s.dependency 'React-Core'
end
