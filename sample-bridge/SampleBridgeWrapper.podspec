Pod::Spec.new do |s|
  s.name             = 'SampleBridgeWrapper'
  s.version          = '0.1.0'
  s.homepage         = 'Link to the Shared Module homepage'
  s.source           = { :http=> ''}
  s.authors          = ''
  s.license          = ''
  s.summary          = 'A short description of SampleBridgeWrapper.'
  s.description      = 'A detailed description of SampleBridgeWrapper.'
  s.license          = ''
  s.ios.deployment_target = '10.0'

  s.source_files = 'wrapper/**/*.swift'

  s.dependency 'SampleBridge'
  s.dependency 'React-Core'
end
