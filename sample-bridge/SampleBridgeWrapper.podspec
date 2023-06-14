#
# Be sure to run `pod lib lint SampleBridge.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see https://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'SampleBridge'
  s.version          = '0.1.0'
  s.summary          = 'A short description of SampleBridge.'

# This description is used to generate tags and improve search results.
#   * Think: What does it do? Why did you write it? What is the focus?
#   * Try to keep it short, snappy and to the point.
#   * Write the description between the DESC delimiters below.
#   * Finally, don't worry about the indent, CocoaPods strips it!

  s.description      = <<-DESC
TODO: Add long description of the pod here.
                       DESC

  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.ios.deployment_target = '10.0'

  s.source_files = '/Users/benny/WorkSpace/external/Klue/sample-bridge/wrapper/**/*.swift'

  s.dependency 'SampleBridge'
  s.dependency 'React-Core'
end
